package parceil.validation.UserProfile;

import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.AdditionalParamForRecipientForm;
import parceil.model.UserProfile.AdditionalParamForProfile.Document;
import parceil.model.UserProfile.UserProfile;
import parceil.validation.ValidatorInterfaces;

import static java.util.Objects.isNull;

public class ProfileValidator implements ValidatorInterfaces<UserProfile> {

    @Override
    public boolean validate(UserProfile profile) {

        if (isNullOrIsEmpty(profile.getFirstName()))
            throw new RuntimeException("Please write first name");

        if (isNullOrIsEmpty(profile.getLastName()))
            throw new RuntimeException("Please write last name");

        if (getAdditionalParamForRecipientForm(profile).isFathersNameRequired()) {
            if (isNullOrIsEmpty(profile.getFathersName()))
                throw new RuntimeException("Please write fathers name");
        }

        if (isNullOrIsEmpty(profile.getCountry().getCountry()))
            throw new RuntimeException("Please choose country");

        if (getAdditionalParamForRecipientForm(profile).isITNRequired()) {
            if (isNull(profile.getITN()) || profile.getITN() == 0L)
                throw new RuntimeException("Please write ITN");
        }

        if (getAdditionalParamForRecipientForm(profile).isPassportsSeriesRequired()) {
            if (isNullOrIsEmpty(profile.getPassportsSeries()))
                throw new RuntimeException("Please write passport series");
        }

        if (getAdditionalParamForRecipientForm(profile).isPassportNumberRequired()) {
            if (isNullOrIsEmpty(profile.getPassportNumber()))
                throw new RuntimeException("Please write passport number");
        }

        if (getAdditionalParamForRecipientForm(profile).isDateOfIssuedRequired()) {
            if (isNull(profile.getDateOfIssued()))
                throw new RuntimeException("Please choose date of issued your passport");
        }

        if (getAdditionalParamForRecipientForm(profile).isPlaceOfIssuedRequired()) {
            if (isNullOrIsEmpty(profile.getPlaceOfIssued()))
                throw new RuntimeException("Please write place of issued your passport");
        }

        if (isNullOrIsEmpty(profile.getRegion())) {
            throw new RuntimeException("Please choose region");
        }

        if (isNullOrIsEmpty(profile.getCity())) {
            throw new RuntimeException("Please choose city");
        }

        if (isNullOrIsEmpty(profile.getStreet())) {
            throw new RuntimeException("Please choose street");
        }

        if (isNull(profile.getTelephoneNumber()) ||
                profile.getTelephoneNumber()
                        .replace(
                                profile.getDirectoryOfRecipientCountries().getCountries()
                                        .stream()
                                        .filter(country -> country.getCountry().equals(profile.getCountry().getCountry()))
                                        .findFirst()
                                        .orElseThrow(() -> new RuntimeException("Country has not found"))
                                        .getTelephoneMask(), "").isEmpty())
            throw new RuntimeException("Please write your telephone number");

        if (isNullOrIsEmpty(profile.getHouse()))
            throw new RuntimeException("Please write your house number");

        if (isNull(profile.getNotification()))
            throw new RuntimeException("Unexpected error");

        if (!isNull(profile.getDocumentList())) {
            for (Document document : profile.getDocumentList()) {
                if (isNullOrIsEmpty(document.getName()))
                    throw new RuntimeException("Wrong country data(name)");
                if (isNull(document.getType()))
                    throw new RuntimeException(document.getName() + "Wrong country data(type)");
                if (isNull(document.getFile()))
                    throw new RuntimeException(document.getName() + "Wrong country data(file)");
                if (isNull(document.getDocumentStatus()))
                    throw new RuntimeException("Wrong country data(documentStatus)");
            }
        }

        return true;
    }

    private boolean isNullOrIsEmpty(String param) {
        return isNull(param) || param.isEmpty();
    }

    private AdditionalParamForRecipientForm getAdditionalParamForRecipientForm(UserProfile profile) {
        return profile.getDirectoryOfRecipientCountries()
                .getCountries()
                .stream()
                .filter(country -> country.getCountry().equals(profile.getCountry().getCountry()))
                .findFirst().orElseThrow(() -> new RuntimeException("Country has not found")).getAdditionalParamForRecipientForm();


    }
}