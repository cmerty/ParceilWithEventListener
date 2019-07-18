package parceil.model.UserProfile;

import lombok.Getter;
import lombok.Setter;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;
import parceil.model.UserProfile.AdditionalParamForProfile.Document;
import parceil.model.UserProfile.AdditionalParamForProfile.Notification;
import parceil.model.UserProfile.AdditionalParamForProfile.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Setter
public class UserProfile {

    private UserStatus userStatus = new UserStatus();

    private String firstName;

    private String lastName;

    private String fathersName;

    private Country country;

    private Long ITN;

    private String passportsSeries;

    private String passportNumber;

    private LocalDateTime dateOfIssued;

    private String placeOfIssued;

    private String region;

    private String city;

    private String street;

    private String telephoneNumber;

    private String house;

    private String apartment;

    private Notification notification;

    private List<Document> documentList = new ArrayList<>();

    private DirectoryOfRecipientCountries directoryOfRecipientCountries = new DirectoryOfRecipientCountries();

    public void setCountry(Country country) {
        this.country = country;
        addCountryToNotification(country);
    }

    public void setTelephoneNumber(String telephoneNumber) {
        for (Country directoryOfRecipientCountriesCountry : directoryOfRecipientCountries.getCountries()) {
            if (directoryOfRecipientCountriesCountry.getCountry().equals(this.country.getCountry()))
                this.telephoneNumber = directoryOfRecipientCountriesCountry.getTelephoneMask() + telephoneNumber;
        }
    }

    private void addCountryToNotification(Country country) {
        if (isNull(notification))
            notification = new Notification();

        if (isNull(notification.getCountries()))
            notification.setCountries(new ArrayList<>());

        notification.getCountries().add(country);
    }

    public void addDocuments(Document document) {
        if (isNull(documentList))
            documentList = new ArrayList<>();

        documentList.add(document);
    }
}

