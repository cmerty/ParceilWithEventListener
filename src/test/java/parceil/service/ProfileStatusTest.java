package parceil.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import parceil.enums.UserProfile.StatusEnum;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;
import parceil.model.UserProfile.AdditionalParamForProfile.Document;
import parceil.model.UserProfile.AdditionalParamForProfile.UserStatus;
import parceil.model.UserProfile.DirectoryOfRecipientCountries;
import parceil.model.UserProfile.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProfileStatusTest {

    private List<UserProfile> userProfileList;

    private UserProfile userProfile;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CounterpartyUpdateStateService counterpartyUpdateStateService;

    @Before
    public void initTest() {
        userProfile = new UserProfile();
        userProfileList = new ArrayList<>();
        userProfileList.add(userProfile);
        String firstDoc = "First";
        String secondDoc = "Second";
        String thirdDoc = "Third";


        DirectoryOfRecipientCountries directoryOfRecipientCountries = new DirectoryOfRecipientCountries();
        List<String> documents = new ArrayList<>();
        documents.add(firstDoc);
        documents.add(secondDoc);
        documents.add(thirdDoc);
        userProfile.setDirectoryOfRecipientCountries(directoryOfRecipientCountries);
        directoryOfRecipientCountries.getCountries().add(new Country("Ukraine", "+38", documents));

    }

    @After
    public void clearAll() {
        userProfile = null;
        userProfileList = null;
    }

    @Test
    public void testProfileWithoutDocument() {
        userProfile.setFirstName("Test_1");
        Country country = new Country();
        country.setCountry("Ukraine");
        userProfile.setCountry(country);
        assertThat(userProfile.getUserStatus().getUserStatus(), is(StatusEnum.AWAITING_FILES));
    }

    @Test
    public void testProfileWithAllDocument() {
        userProfile.setFirstName("Test_2");
        Country country = userProfile.getDirectoryOfRecipientCountries()
                .getCountries()
                .stream()
                .filter(country1 -> country1.getCountry().equals("Ukraine"))
                .findFirst().orElseThrow(() -> new RuntimeException("Not found exception"));
        userProfile.setCountry(country);

        counterpartyUpdateStateService.setUserRepository(userProfileList);

        Document first = new Document();
        first.setName("First");
        Document second = new Document();
        second.setName("Second");
        Document third = new Document();
        third.setName("Third");
        userProfile.addDocuments(first);
        userProfile.addDocuments(second);
        userProfile.addDocuments(third);
        countryService.setCountries(userProfile.getDirectoryOfRecipientCountries().getCountries());
        userProfile.setUserStatus(new UserStatus(StatusEnum.AWAITING_VERIFICATION));
        countryService.addDocument(country.getId(), "new Passport");

        assertThat(userProfile.getUserStatus().getUserStatus(), is(StatusEnum.AWAITING_FILES));
    }


    @Test
    public void testProfileWithoutOneDocument() {
        userProfile.setFirstName("Test_3");
        Country country = new Country();
        country.setCountry("Ukraine");
        userProfile.setCountry(country);

        Document first = new Document();
        first.setName("First");
        Document second = new Document();
        second.setName("Second");

        userProfile.addDocuments(first);
        userProfile.addDocuments(second);


        assertThat(userProfile.getUserStatus().getUserStatus(), is(StatusEnum.AWAITING_FILES));
    }
}
