import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parceil.enums.UserProfile.TypeOfDocumentEnum;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.AdditionalParamForRecipientForm;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;
import parceil.model.UserProfile.AdditionalParamForProfile.Document;
import parceil.model.UserProfile.AdditionalParamForProfile.DocumentStatus;
import parceil.model.UserProfile.AdditionalParamForProfile.Type;
import parceil.model.UserProfile.DirectoryOfRecipientCountries;
import parceil.model.UserProfile.UserProfile;
import parceil.validation.UserProfile.ProfileValidator;
import parceil.validation.ValidatorInterfaces;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class ProfileValidatorTest {

    private UserProfile profile;

    private DirectoryOfRecipientCountries directoryOfRecipientCountries;

    private ValidatorInterfaces validator;

    @Before
    public void initTest() {
        profile = new UserProfile();
        directoryOfRecipientCountries = new DirectoryOfRecipientCountries();
        Country ukraine = new Country("Ukraine", "+38");
        Country usa = new Country("USA", "+1");
        Country israel = new Country("Israel", "+972");
        directoryOfRecipientCountries.getCountries().add(ukraine);
        directoryOfRecipientCountries.getCountries().add(usa);
        directoryOfRecipientCountries.getCountries().add(israel);
        profile.setDirectoryOfRecipientCountries(directoryOfRecipientCountries);
        validator = new ProfileValidator();
    }

    @After
    public void afterTest() {
        profile = null;
        directoryOfRecipientCountries = null;
        validator = null;
    }

    @Test
    public void fullyCorrectProfileWithAllData() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("Heroiv Dnipra");
        profile.setTelephoneNumber("0950385058");
        profile.setHouse("19");
        profile.setApartment("188");
        Document document = new Document();
        document.setName("Passport");
        document.setType(new Type(TypeOfDocumentEnum.SMALL.toString()));
        String myDocs = "MyDocs";
        document.setFile(myDocs.getBytes());
        document.setDocumentStatus(new DocumentStatus());
        profile.addDocuments(document);
        assertThat(validator.validate(profile), is(true));
    }


    @Test(expected = RuntimeException.class)
    public void profileOnlyWithName() {
        profile.setFirstName("Andrii");
        profile.setLastName("");
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileOnlyWithNameAndLastName() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("");
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToCountry() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        profile.setCountry(null);
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToITN() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(0L);
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToPassportSeries() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("");
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToPassportNumber() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("");
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToIssuedDate() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        profile.setDateOfIssued(null);
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToPlaceOfIssued() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("");
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToRegion() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("");
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToCity() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("");
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToStreet() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("");
        validator.validate(profile);
    }


    @Test(expected = RuntimeException.class)
    public void profileFromNameToPhoneNumber() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("Heroiv Dnipra");
        profile.setTelephoneNumber("");
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToHouse() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("Heroiv Dnipra");
        profile.setTelephoneNumber("0950385058");
        profile.setHouse("");
        validator.validate(profile);
    }

    @Test()
    public void profileFromNameToApartment() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("Heroiv Dnipra");
        profile.setTelephoneNumber("0950385058");
        profile.setHouse("19");
        profile.setApartment("");
        assertThat(validator.validate(profile), is(true));
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToDocumentWithoutName() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("Heroiv Dnipra");
        profile.setTelephoneNumber("0950385058");
        profile.setHouse("188");
        Document document = new Document();
        document.setName("");
        document.setType(new Type(TypeOfDocumentEnum.SMALL.toString()));
        document.setDocumentStatus(new DocumentStatus());
        String myDocs = "MyDocs";
        document.setFile(myDocs.getBytes());
        profile.addDocuments(document);
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToDocumentWithoutType() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("Heroiv Dnipra");
        profile.setTelephoneNumber("0950385058");
        profile.setHouse("188");
        Document document = new Document();
        document.setName("Passport");
        document.setType(null);
        String myDocs = "MyDocs";
        document.setFile(myDocs.getBytes());
        document.setDocumentStatus(new DocumentStatus());
        profile.addDocuments(document);
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToDocumentWithoutFile() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("Heroiv Dnipra");
        profile.setTelephoneNumber("0950385058");
        profile.setHouse("188");
        Document document = new Document();
        document.setName("Passport");
        document.setType(new Type(TypeOfDocumentEnum.SMALL.toString()));
        document.setFile(null);
        document.setDocumentStatus(new DocumentStatus());
        profile.addDocuments(document);
        validator.validate(profile);
    }

    @Test(expected = RuntimeException.class)
    public void profileFromNameToDocumentWithoutStatus() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        getAdditionalParamForRecipientForm().setFathersNameRequired(true);
        profile.setFathersName("Vladymyrovych");
        Country country = directoryOfRecipientCountries.getCountries().stream().filter(countries -> countries.getCountry().equals("Ukraine")).findFirst().get();
        profile.setCountry(country);
        getAdditionalParamForRecipientForm().setITNRequired(true);
        profile.setITN(1231412023L);
        getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        profile.setPassportsSeries("TT");
        getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        profile.setPassportNumber("084116");
        getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        LocalDateTime now = LocalDateTime.now();
        profile.setDateOfIssued(now);
        getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        profile.setPlaceOfIssued("Obolon RyGy MVS of Ukraine");
        profile.setRegion("Kiev");
        profile.setCity("Kiev");
        profile.setStreet("Heroiv Dnipra");
        profile.setTelephoneNumber("0950385058");
        profile.setHouse("188");
        Document document = new Document();
        document.setName("Passport");
        document.setType(new Type(TypeOfDocumentEnum.SMALL.toString()));
        String myDocs = "MyDocs";
        document.setFile(myDocs.getBytes());
        document.setDocumentStatus(null);
        profile.addDocuments(document);
        validator.validate(profile);
    }

    private AdditionalParamForRecipientForm getAdditionalParamForRecipientForm() {
        return profile.getDirectoryOfRecipientCountries()
                .getCountries()
                .stream()
                .filter(country -> country.getCountry().equals(profile.getCountry().getCountry()))
                .findFirst().orElseThrow(() -> new RuntimeException("Country has not found")).getAdditionalParamForRecipientForm();


    }
}
