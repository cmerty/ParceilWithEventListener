package parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries;

import lombok.Getter;
import lombok.Setter;
import parceil.enums.UserProfile.TypeOfDeliveryEnum;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Setter
public class Country {

    private static Long counter = 0L;
    private Long id;
    private String country;
    private String telephoneMask;
    private List<String> requiredDocument;
    private AdditionalParamForRecipientForm additionalParamForRecipientForm = new AdditionalParamForRecipientForm();
    private AdditionalParamInGoodsForm additionalParamInGoodsForm = new AdditionalParamInGoodsForm();
    private AdditionalParamInOrderForm additionalParamInOrderForm = new AdditionalParamInOrderForm();
    private Long criticalValueOfCostNotTaxable;
    private TypeOfDeliveryEnum typeOfDeliveryEnum;
    private VolumeWeight volumeweight;
    private double maxAllowableValueOfOneParcel;
    private double maxAllowableWeightOfOneParcel;

    public Country() {
        counter++;
        this.id = counter;
    }

    public Country(String country, String telephoneMask, List<String> requiredDocument, AdditionalParamForRecipientForm additionalParamForRecipientForm, AdditionalParamInGoodsForm additionalParamInGoodsForm, AdditionalParamInOrderForm additionalParamInOrderForm, Long criticalValueOfCostNotTaxable, TypeOfDeliveryEnum typeOfDeliveryEnum, VolumeWeight volumeweight, double maxAllowableValueOfOneParcel, double maxAllowableWeightOfOneParcel) {
        counter++;
        this.id = counter;
        this.country = country;
        this.telephoneMask = telephoneMask;
        this.requiredDocument = requiredDocument;
        this.additionalParamForRecipientForm = additionalParamForRecipientForm;
        this.additionalParamInGoodsForm = additionalParamInGoodsForm;
        this.additionalParamInOrderForm = additionalParamInOrderForm;
        this.criticalValueOfCostNotTaxable = criticalValueOfCostNotTaxable;
        this.typeOfDeliveryEnum = typeOfDeliveryEnum;
        this.volumeweight = volumeweight;
        this.maxAllowableValueOfOneParcel = maxAllowableValueOfOneParcel;
        this.maxAllowableWeightOfOneParcel = maxAllowableWeightOfOneParcel;
    }

    public Country(String country, String telephoneMask, List<String> requiredDocument) {
        this.country = country;
        this.telephoneMask = telephoneMask;
        this.requiredDocument = requiredDocument;
        counter++;
        this.id = counter;
    }

    public Country(String country, String telephoneMask) {
        this.country = country;
        this.telephoneMask = telephoneMask;
        counter++;
        this.id = counter;
    }

    public void addDocument(String nameOfDocument) {
        if (isNull(requiredDocument))
            requiredDocument = new ArrayList<>();

        requiredDocument.add(nameOfDocument);
    }

}
