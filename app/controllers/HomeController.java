package controllers;

import javax.inject.Inject;

import forms.authorization.CreateOrganizationForm;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

    private final FormFactory formFactory;
    
    @Inject
    public HomeController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }
    
    public Result index() {
        return ok(views.html.index.render(this.formFactory.form(CreateOrganizationForm.class)));
    }
    
    public Result handleForm() {
        final Form<CreateOrganizationForm> submittedForm = this.formFactory.form(CreateOrganizationForm.class).bindFromRequest();
        if(submittedForm.hasErrors()) {
            Logger.debug("Form has errors:");
            Logger.debug("~~~~");
            for(final ValidationError error: submittedForm.allErrors()) {
                Logger.debug(error.key());
                Logger.debug("~~~~");
                error.messages().forEach(Logger::debug);
                Logger.debug("~~~~");
                Logger.debug(error.message());
                Logger.debug("~~~~");
            }
            Logger.debug(submittedForm.errorsAsJson().toString());
            return badRequest(views.html.index.render(submittedForm));
        } else {
            return ok("success");
        }        
    }

}
