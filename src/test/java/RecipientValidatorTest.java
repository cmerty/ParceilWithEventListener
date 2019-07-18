import parceil.model.Recipient.DirectoryOfRecipientCountries;
import parceil.model.Recipient.Recipient;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parceil.validation.Recipient.RecipientValidator;
import parceil.validation.ValidatorInterfaces;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecipientValidatorTest {

    private Recipient recipient;

    private DirectoryOfRecipientCountries directoryOfRecipientCountries;

    private ValidatorInterfaces validator;

    @Before
    public void initTest() {
        recipient = new Recipient();
        directoryOfRecipientCountries = new DirectoryOfRecipientCountries();
        Country ukraine = new Country("Ukraine", "+38");
        Country usa = new Country("USA", "+1");
        Country israel = new Country("Israel", "+972");
        directoryOfRecipientCountries.getCountries().add(ukraine);
        directoryOfRecipientCountries.getCountries().add(usa);
        directoryOfRecipientCountries.getCountries().add(israel);
        recipient.setDirectoryOfRecipientCountries(directoryOfRecipientCountries);
        validator = new RecipientValidator();
    }

    @After
    public void afterTest() {
        recipient = null;
        directoryOfRecipientCountries = null;
        validator = null;
    }

    @Test
    public void fullyCorrectRecipientWithAllData() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Client");
        recipient.setLastName("Important");
        recipient.setFathersName("Very");
        recipient.setCountry("Ukraine");
        recipient.setRegion("Kyiv");
        recipient.setCity("Kyiv");
        recipient.setStreet("Frunze str.");
        recipient.setTelephone("12345678");
        recipient.setDepartment("№252");
        recipient.getDirectoryOfRecipientCountries().getAdditionalParamForRecipientForm().setPassportsSeriesRequired(true);
        recipient.setPassportsSeries("TT");
        recipient.getDirectoryOfRecipientCountries().getAdditionalParamForRecipientForm().setPassportNumberRequired(true);
        recipient.setPassportNumber("123456");
        recipient.getDirectoryOfRecipientCountries().getAdditionalParamForRecipientForm().setDateOfIssuedRequired(true);
        recipient.setDateOfIssued(LocalDateTime.now());
        recipient.getDirectoryOfRecipientCountries().getAdditionalParamForRecipientForm().setPlaceOfIssuedRequired(true);
        recipient.setPlaceOfIssued("Pushkin street");
        recipient.getDirectoryOfRecipientCountries().getAdditionalParamForRecipientForm().setITNRequired(true);
        recipient.setITN(1234567L);
        assertThat(validator.validate(recipient), is(true));
    }

    @Test(expected = RuntimeException.class)
    public void recipientWithoutAnything() {
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientWithReceivingPlace() {
        recipient.setReceivingPlace("Department 123");
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientForReceivingPlaceToFirstName() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientForReceivingPlaceToLastName() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        recipient.setLastName("Prysiazhniuk");
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientForReceivingPlaceToFathersName() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        recipient.setLastName("Prysiazhniuk");
        recipient.setFathersName("Vladymyrovych");
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientForReceivingPlaceToCountry() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        recipient.setLastName("Prysiazhniuk");
        recipient.setFathersName("Vladymyrovych");
        recipient.setCountry("Ukraine");
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientForReceivingPlaceToRegion() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        recipient.setLastName("Prysiazhniuk");
        recipient.setFathersName("Vladymyrovych");
        recipient.setCountry("Ukraine");
        recipient.setRegion("Kyiv");
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientForReceivingPlaceToCity() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        recipient.setLastName("Prysiazhniuk");
        recipient.setFathersName("Vladymyrovych");
        recipient.setCountry("Ukraine");
        recipient.setRegion("Kyiv");
        recipient.setCity("Kyiv");
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientForReceivingPlaceToStreet() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        recipient.setLastName("Prysiazhniuk");
        recipient.setFathersName("Vladymyrovych");
        recipient.setCountry("Ukraine");
        recipient.setRegion("Kyiv");
        recipient.setCity("Kyiv");
        recipient.setStreet("Frunze str.");
        validator.validate(recipient);
    }

    @Test(expected = RuntimeException.class)
    public void recipientForReceivingPlaceToPhone() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        recipient.setLastName("Prysiazhniuk");
        recipient.setFathersName("Vladymyrovych");
        recipient.setCountry("Ukraine");
        recipient.setRegion("Kyiv");
        recipient.setCity("Kyiv");
        recipient.setStreet("Frunze str.");
        recipient.setTelephone("12345678");
        validator.validate(recipient);
    }

    @Test
    public void recipientForReceivingPlaceToDepartment() {
        recipient.setReceivingPlace("Department 123");
        recipient.setFirstName("Andrii");
        recipient.setLastName("Prysiazhniuk");
        recipient.setFathersName("Vladymyrovych");
        recipient.setCountry("Ukraine");
        recipient.setRegion("Kyiv");
        recipient.setCity("Kyiv");
        recipient.setStreet("Frunze str.");
        recipient.setTelephone("12345678");
        recipient.setDepartment("№1231");
        assertThat(validator.validate(recipient), is(true));
    }
}
