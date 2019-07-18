package parceil.model.Recipient;

import lombok.Getter;
import lombok.Setter;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;

import java.time.LocalDateTime;

@Getter
@Setter
public class Recipient {

    private String receivingPlace;

    private String firstName;

    private String lastName;

    private String fathersName;

    private String country;

    private String region;

    private String city;

    private String street;

    private String index;

    private String house;

    private String apartment;

    private String telephone;

    private String department;

    private DirectoryOfRecipientCountries directoryOfRecipientCountries = new DirectoryOfRecipientCountries();

    private String passportsSeries;

    private String passportNumber;

    private LocalDateTime dateOfIssued;

    private String placeOfIssued;

    private Long ITN;

    public void setTelephone(String telephone) {
        for (Country directoryOfRecipientCountriesCountry : directoryOfRecipientCountries.getCountries()) {
            if (directoryOfRecipientCountriesCountry.getCountry().equals(this.country))
                this.telephone = directoryOfRecipientCountriesCountry.getTelephoneMask() + telephone;
        }
    }
}
