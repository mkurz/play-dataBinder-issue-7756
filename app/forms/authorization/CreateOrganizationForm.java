package forms.authorization;

import com.google.i18n.phonenumbers.Phonenumber;
import play.data.validation.Constraints;

public class CreateOrganizationForm {

    @Constraints.Required
    //@Constraints.Pattern(value = "^\\+33 [1-9] [0-9]{2} [0-9]{2} [0-9]{2} [0-9]{2}$", message = "error.phoneNumber")
    private Phonenumber.PhoneNumber phone;

    // other fields

    public Phonenumber.PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(Phonenumber.PhoneNumber phone) {
        this.phone = phone;
    }

    // other getters & setters
}