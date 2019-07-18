package parceil.validation.RegistrationPurchase;

import parceil.model.RegistrationPurchase.Purchase;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.AdditionalParamInGoodsForm;
import parceil.model.UserProfile.UserProfile;
import parceil.validation.ValidatorInterfaces;

import static java.util.Objects.isNull;

public class PurchaseValidator implements ValidatorInterfaces<Purchase> {

    @Override
    public boolean validate(Purchase purchase) {

        if (isNull(purchase.getUserProfile()))
            throw new RuntimeException("Please populate your profile before make purchase");

        if (isNullOrIsEmpty(purchase.getStock().getName()))
            throw new RuntimeException("Please choose stock");

        if (isNullOrIsEmpty(purchase.getDeclarationNumber()))
            throw new RuntimeException("Please write declaration number");

        if (isNull(purchase.getGoods()) || purchase.getGoods().size() == 0)
            throw new RuntimeException("Please create new position");

        if (purchase.getGoods().stream().anyMatch(position -> isNull(position.getSubCategory())) || purchase.getGoods().stream().anyMatch(position -> isNull(position.getSubCategory().getCategory())))
            throw new RuntimeException("Please choose category or subcategory");

        if (purchase.getGoods().stream().anyMatch(position -> isNull(position.getName())))
            throw new RuntimeException("Please choose goodsSettings");

        if (getAdditionalParamInGoodsForm(purchase.getUserProfile()).isShopRequired()) {
            if (purchase.getGoods().stream().anyMatch(position -> isNullOrIsEmpty(position.getShop())))
                throw new RuntimeException("Please write required shop");
        }

        if (purchase.getGoods()
                .stream()
                .filter(position -> position.getGoodsSettings().isAdditionalDescriptionIsRequired())
                .anyMatch(goods -> isNullOrIsEmpty(goods.getDescriptionModelArticle())))
            throw new RuntimeException("Please write description/parceil.model/article");

        if (purchase.getStock().getTypeOfDelivery().equals("AVIA")) {
            if (purchase.getStock().isContainBatteriesLiquidsOrPowders()) {
                if (purchase.getGoods()
                        .stream()
                        .noneMatch(position -> isNull(position.getGoodsSettings().getDangerous())) &&
                        (purchase.getGoods()
                                .stream()
                                .anyMatch(position -> isNull(position.getHazardousSubstances()))))
                    throw new RuntimeException("Please write hazardous substances");
            }
        }

        if (purchase.getGoods()
                .stream()
                .anyMatch(position -> isSmallerThan1OrBiggerThan9999(position.getQuantity())))
            throw new RuntimeException("Please write correct count of goodsSettings");

        if (purchase.getGoods()
                .stream()
                .anyMatch(position -> isSmallerThan1OrBiggerThan9999(position.getCost())))
            throw new RuntimeException("Please write correct cost of goodsSettings");

        if (purchase.isImmediatelyDelivery() && isNull(purchase.getRecipient()))
            throw new RuntimeException("Please populate recipient field");

        return true;
    }

    private boolean isNullOrIsEmpty(String param) {
        return isNull(param) || param.isEmpty();
    }

    private boolean isSmallerThan1OrBiggerThan9999(int quantity) {
        return quantity > 9999 || quantity < 1;
    }

    private boolean isSmallerThan1OrBiggerThan9999(double quantity) {
        return quantity > 9999 || quantity < 1;
    }

    private AdditionalParamInGoodsForm getAdditionalParamInGoodsForm(UserProfile profile) {
        return profile.getDirectoryOfRecipientCountries()
                .getCountries()
                .stream()
                .filter(country -> country.getCountry().equals(profile.getCountry().getCountry()))
                .findFirst().orElseThrow(() -> new RuntimeException("Country has not found")).getAdditionalParamInGoodsForm();


    }
}
