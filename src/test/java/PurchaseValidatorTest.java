import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parceil.enums.RegistrationPurchase.FormFactorEnum;
import parceil.enums.UserProfile.TypeOfDeliveryEnum;
import parceil.enums.UserProfile.TypeOfDocumentEnum;
import parceil.model.Recipient.Recipient;
import parceil.model.RegistrationPurchase.AdditionalParamForPurchase.AdditionalServicesForPurchase;
import parceil.model.RegistrationPurchase.Category;
import parceil.model.RegistrationPurchase.Goods;
import parceil.model.RegistrationPurchase.Purchase;
import parceil.model.RegistrationPurchase.SubCategory;
import parceil.model.Stock.LittleBox;
import parceil.model.Stock.Stock;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.AdditionalParamForRecipientForm;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.AdditionalParamInGoodsForm;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;
import parceil.model.UserProfile.AdditionalParamForProfile.Document;
import parceil.model.UserProfile.AdditionalParamForProfile.DocumentStatus;
import parceil.model.UserProfile.AdditionalParamForProfile.Type;
import parceil.model.UserProfile.DirectoryOfRecipientCountries;
import parceil.model.UserProfile.UserProfile;
import parceil.validation.RegistrationPurchase.PurchaseValidator;
import parceil.validation.ValidatorInterfaces;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PurchaseValidatorTest {

    private UserProfile profile;

    private Purchase purchase;

    private Category category;

    private SubCategory subCategory;

    private Stock stock;

    private ValidatorInterfaces validator;

    private DirectoryOfRecipientCountries directoryOfRecipientCountries;

    @Before
    public void initTest() {
        profile = new UserProfile();
        profile = createUser();
        directoryOfRecipientCountries = new DirectoryOfRecipientCountries();
        Country ukraine = new Country("Ukraine", "+38");
        Country usa = new Country("USA", "+1");
        Country israel = new Country("Israel", "+972");
        directoryOfRecipientCountries.getCountries().add(ukraine);
        directoryOfRecipientCountries.getCountries().add(usa);
        directoryOfRecipientCountries.getCountries().add(israel);
        profile.setDirectoryOfRecipientCountries(directoryOfRecipientCountries);
        stock = new Stock();
        stock = createStock();
        purchase = new Purchase();
        category = new Category();
        subCategory = new SubCategory();
        validator = new PurchaseValidator();
        directoryOfRecipientCountries = new DirectoryOfRecipientCountries();
    }

    @After
    public void afterTest() {
        profile = null;
        stock = null;
        purchase = null;
        category = null;
        subCategory = null;
        validator = null;
        directoryOfRecipientCountries = null;
    }

    @Test
    public void fullyCorrectPurchaseWithAllData() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        subCategory.setSubCategoryName("Volvo");
        subCategory.setCategory(category);
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        goods.setName("new cool thing");
        getAdditionalParamInGoodsForm().setShopRequired(true);
        goods.setShop("new Store");
        goods.getGoodsSettings().setAdditionalDescriptionIsRequired(true);
        goods.setDescriptionModelArticle("nice description for nice goodsSettings");
        goods.getGoodsSettings().setDangerous(true);
        goods.setHazardousSubstances(false);
        goods.setPhotosLinks("wwww.links.com,link2.com,hello_world.com");
        purchase.addGoods(goods);
        goods.setQuantity(25);
        goods.setCost(10.5);
        AdditionalServicesForPurchase additionalServicesForPurchase = new AdditionalServicesForPurchase();
        additionalServicesForPurchase.set1(true);
        additionalServicesForPurchase.set2(true);
        additionalServicesForPurchase.set3(false);
        purchase.setAdditionalServicesForPurchase(additionalServicesForPurchase);
        purchase.getNames();
        purchase.setFormFactor(FormFactorEnum.SQUARE.toString());
        purchase.setFragile(true);
        purchase.setImmediatelyDelivery(true);
        Recipient recipient = new Recipient();
        purchase.setRecipient(recipient);
        assertThat(validator.validate(purchase), is(true));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseWithoutProfileButWithStock() {
        purchase.setUserProfile(null);
        purchase.setStock(stock);
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOnlyWithProfile() {
        purchase.setUserProfile(profile);
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToStock() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToDeclarationNumber() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToNewPosition() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        purchase.addGoods(goods);
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToCategory() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        subCategory.setCategory(category);
        purchase.addGoods(goods);
        subCategory.setSubCategoryName("Volvo");
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToGoodsName() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        purchase.addGoods(goods);
        subCategory.setSubCategoryName("Volvo");
        subCategory.setCategory(category);
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        goods.setName("Things");
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToShop() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        purchase.addGoods(goods);
        subCategory.setSubCategoryName("Volvo");
        subCategory.setCategory(category);
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        goods.setName("Things");
        getAdditionalParamInGoodsForm().setShopRequired(true);
        goods.setShop("new Store");
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToGoodsDescIsRequired() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        purchase.addGoods(goods);
        subCategory.setSubCategoryName("Volvo");
        subCategory.setCategory(category);
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        goods.setName("Things");
        getAdditionalParamInGoodsForm().setShopRequired(true);
        goods.setShop("new Store");
        goods.getGoodsSettings().setAdditionalDescriptionIsRequired(true);
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToAdditionalDesc() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        purchase.addGoods(goods);
        subCategory.setSubCategoryName("Volvo");
        subCategory.setCategory(category);
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        goods.setName("Things");
        getAdditionalParamInGoodsForm().setShopRequired(true);
        goods.setShop("new Store");
        goods.getGoodsSettings().setAdditionalDescriptionIsRequired(true);
        goods.setDescriptionModelArticle("nice description for nice goodsSettings");
        goods.getGoodsSettings().setDangerous(true);
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToDangerous() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        purchase.addGoods(goods);
        subCategory.setSubCategoryName("Volvo");
        subCategory.setCategory(category);
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        goods.setName("Things");
        getAdditionalParamInGoodsForm().setShopRequired(true);
        goods.setShop("new Store");
        goods.getGoodsSettings().setAdditionalDescriptionIsRequired(true);
        goods.setDescriptionModelArticle("nice description for nice goodsSettings");
        goods.getGoodsSettings().setDangerous(true);
        goods.setHazardousSubstances(false);
        validator.validate(purchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseForProfileToQuantity() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        purchase.addGoods(goods);
        subCategory.setSubCategoryName("Volvo");
        subCategory.setCategory(category);
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        goods.setName("Things");
        getAdditionalParamInGoodsForm().setShopRequired(true);
        goods.setShop("new Store");
        goods.getGoodsSettings().setAdditionalDescriptionIsRequired(true);
        goods.setDescriptionModelArticle("nice description for nice goodsSettings");
        goods.getGoodsSettings().setDangerous(true);
        goods.setHazardousSubstances(false);
        goods.setQuantity(120);
        validator.validate(purchase);
    }

    @Test
    public void purchaseForProfileToCost() {
        purchase.setUserProfile(profile);
        purchase.setStock(stock);
        purchase.setDeclarationNumber("CM123456789UA");
        Goods goods = new Goods();
        purchase.addGoods(goods);
        subCategory.setSubCategoryName("Volvo");
        subCategory.setCategory(category);
        category.setCategoryName("Car");
        category.addSubCategory(subCategory);
        goods.setSubCategory(subCategory);
        goods.setName("Things");
        getAdditionalParamInGoodsForm().setShopRequired(true);
        goods.setShop("new Store");
        goods.getGoodsSettings().setAdditionalDescriptionIsRequired(true);
        goods.setDescriptionModelArticle("nice description for nice goodsSettings");
        goods.getGoodsSettings().setDangerous(true);
        goods.setHazardousSubstances(false);
        goods.setQuantity(120);
        goods.setCost(15);
        assertThat(validator.validate(purchase), is(true));
    }

    private UserProfile createUser() {
        profile.setFirstName("Andrii");
        profile.setLastName("Prysiazhniuk");
        directoryOfRecipientCountries = new DirectoryOfRecipientCountries();
        Country ukraine = new Country("Ukraine", "+38");
        Country usa = new Country("USA", "+1");
        Country israel = new Country("Israel", "+972");
        directoryOfRecipientCountries.getCountries().add(ukraine);
        directoryOfRecipientCountries.getCountries().add(usa);
        directoryOfRecipientCountries.getCountries().add(israel);
        profile.setDirectoryOfRecipientCountries(directoryOfRecipientCountries);
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
        return profile;
    }

    private Stock createStock() {
        stock.setName("Nice Stock");
        stock.setCountry("Ukraine");
        stock.setAddress("HVDnipra");
        stock.setEmail("stock@gmail.com");
        stock.setTelephoneNumber("+38912319283912");
        stock.setNameForDocumentation("THE NICE STOCK INCORP.");
        stock.setFullNameOfResponsiblePerson("Director");
        stock.setCoefficient(1231);
        stock.setCurrency("UAH");
        stock.setContainBatteriesLiquidsOrPowders(true);
        stock.setDisplayInTheCabinet(true);
        stock.setBuyoutBan(true);
        stock.setDaysOfFreeStorage(100);
        stock.setTypeOfDelivery(TypeOfDeliveryEnum.AVIA.toString());
        stock.setPrefix("CM");
        stock.setSuffix("UA");
        LittleBox littleBox = new LittleBox();
        littleBox.setMaxWeight(100);
        littleBox.setLengthWidthHeight(5, 10, 2);
        stock.setLittleBox(littleBox);
        return stock;

    }

    private AdditionalParamForRecipientForm getAdditionalParamForRecipientForm() {
        return profile.getDirectoryOfRecipientCountries()
                .getCountries()
                .stream()
                .filter(country -> country.getCountry().equals(profile.getCountry().getCountry()))
                .findFirst().orElseThrow(() -> new RuntimeException("Country has not found")).getAdditionalParamForRecipientForm();
    }

    private AdditionalParamInGoodsForm getAdditionalParamInGoodsForm() {
        return profile.getDirectoryOfRecipientCountries()
                .getCountries()
                .stream()
                .filter(country -> country.getCountry().equals(profile.getCountry().getCountry()))
                .findFirst().orElseThrow(() -> new RuntimeException("Country has not found")).getAdditionalParamInGoodsForm();


    }
}
