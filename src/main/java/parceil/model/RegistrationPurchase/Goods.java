package parceil.model.RegistrationPurchase;

import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
public class Goods {

    private SubCategory subCategory;

    private String name;

    private GoodsSettings goodsSettings = new GoodsSettings();

    private String descriptionModelArticle;

    private Boolean hazardousSubstances;

    private String shop;

    private int quantity;

    private double cost;

    private String photosLinks;

    private String typeOfGoods;


    public void setHazardousSubstances(boolean hazardousSubstances) {

        if (isNull(this.goodsSettings.getDangerous()))
            this.hazardousSubstances = hazardousSubstances;

        if (this.goodsSettings.getDangerous())
            this.hazardousSubstances = true;

        if (!this.goodsSettings.getDangerous())
            this.hazardousSubstances = false;
    }

    public void setGood(GoodsSettings good) {
        if (isNull(this.goodsSettings))
            this.goodsSettings = new GoodsSettings();
        this.goodsSettings = good;
    }
}
