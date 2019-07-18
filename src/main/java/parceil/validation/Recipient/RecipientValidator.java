package parceil.validation.Recipient;

import parceil.model.Recipient.AdditionalParamDirectoryOfRecipientCountries.AdditionalParamForRecipientForm;
import parceil.model.Recipient.Recipient;
import parceil.validation.ValidatorInterfaces;

import static java.util.Objects.isNull;

public class RecipientValidator implements ValidatorInterfaces<Recipient> {

    @Override
    public boolean validate(Recipient recipient) {

        AdditionalParamForRecipientForm additionalParamForRecipientForm = recipient.getDirectoryOfRecipientCountries().getAdditionalParamForRecipientForm();

        if (isNullOrIsEmpty(recipient.getReceivingPlace()))
            throw new RuntimeException("Please choose receiving place");

        if (isNullOrIsEmpty(recipient.getFirstName()))
            throw new RuntimeException("Please write your first name");

        if (isNullOrIsEmpty(recipient.getLastName()))
            throw new RuntimeException("Please write your last name");

        if (isNullOrIsEmpty(recipient.getFathersName()))
            throw new RuntimeException("Please write your fathers name");

        if (isNullOrIsEmpty(recipient.getCountry()))
            throw new RuntimeException("Please write choose your country");

        if (isNullOrIsEmpty(recipient.getRegion()))
            throw new RuntimeException("Please choose region");

        if (isNullOrIsEmpty(recipient.getCity()))
            throw new RuntimeException("Please choose city");

        if (isNullOrIsEmpty(recipient.getStreet()))
            throw new RuntimeException("Please choose street");

        if (isNull(recipient.getTelephone()) ||
                recipient.getTelephone()
                        .replace(
                                recipient.getDirectoryOfRecipientCountries().getCountries()
                                        .stream()
                                        .filter(country -> country.getCountry().equals(recipient.getCountry()))
                                        .findFirst()
                                        .orElseThrow(()->new RuntimeException("Country has not found"))
                                        .getTelephoneMask(), "").isEmpty())
            throw new RuntimeException("Please write your telephone number");

        if (isNullOrIsEmpty(recipient.getDepartment()))
            throw new RuntimeException("Please choose department");

        if (additionalParamForRecipientForm.isPassportsSeriesRequired() && isNullOrIsEmpty(recipient.getPassportsSeries()))
            throw new RuntimeException("Please write your passport series");

        if (additionalParamForRecipientForm.isPassportNumberRequired() && isNullOrIsEmpty(recipient.getPassportNumber()))
            throw new RuntimeException("Please write your passport number");

        if (additionalParamForRecipientForm.isDateOfIssuedRequired() && isNull(recipient.getDateOfIssued()))
            throw new RuntimeException("Please choose date of issued");

        if (additionalParamForRecipientForm.isPlaceOfIssuedRequired() && isNullOrIsEmpty(recipient.getPlaceOfIssued()))
            throw new RuntimeException("Please write place of issued");

        if (additionalParamForRecipientForm.isITNRequired() && isNull(recipient.getITN()))
            throw new RuntimeException("Please write your individual tax number ");

        return true;
    }

    private boolean isNullOrIsEmpty(String param) {
        return isNull(param) || param.isEmpty();
    }

}
