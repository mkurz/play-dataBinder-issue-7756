package modules.formatter;

import java.text.ParseException;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;
import play.i18n.MessagesApi;


@Singleton
public class FormattersProvider implements Provider<Formatters> {

    private final MessagesApi messagesApi;

    @Inject
    public FormattersProvider(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Override
    public Formatters get() {
        Formatters formatters = new Formatters(messagesApi);
        formatters.register(Phonenumber.PhoneNumber.class, getPhoneNumberFormatter());

        return formatters;
    }

    private SimpleFormatter<Phonenumber.PhoneNumber> getPhoneNumberFormatter() {
        return new SimpleFormatter<Phonenumber.PhoneNumber>() {
            @Override
            public Phonenumber.PhoneNumber parse(String input, Locale l) throws ParseException {
                try {
                    Phonenumber.PhoneNumber phoneNumber = PhoneNumberUtil.getInstance().parse(input, "FR");
                    if (!PhoneNumberUtil.getInstance().isValidNumberForRegion(phoneNumber, "FR")) {
                        throw new ParseException("", 0);
                    }

                    return phoneNumber;
                } catch (NumberParseException e) {
                    throw new ParseException("", 0);
                }
            }

            @Override
            public String print(Phonenumber.PhoneNumber phoneNumber, Locale l) {
                return PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
            }
        };
    }
}