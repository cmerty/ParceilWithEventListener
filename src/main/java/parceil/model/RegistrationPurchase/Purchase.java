package parceil.model.RegistrationPurchase;

import parceil.enums.RegistrationPurchase.TypeOfGoodsEnum;
import lombok.Getter;
import lombok.Setter;
import parceil.model.Recipient.Recipient;
import parceil.model.RegistrationPurchase.AdditionalParamForPurchase.AdditionalServicesForPurchase;
import parceil.model.Stock.Stock;
import parceil.model.UserProfile.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Setter
public class Purchase {

    private UserProfile userProfile;

    private Stock stock;

    private String declarationNumber;

    private List<Goods> goods;

    private int totalQuantity;

    private double totalAmount;

    private AdditionalServicesForPurchase additionalServicesForPurchase;

    private String names;

    private String formFactor;

    private boolean isFragile;

    private boolean immediatelyDelivery;

    private Recipient recipient;

    public int getTotalQuantity() {
        goods.forEach(position -> this.totalQuantity += position.getQuantity());
        return totalQuantity;
    }

    public double getTotalAmount() {
        goods.forEach(position -> this.totalAmount += position.getCost());
        return totalAmount;
    }

    public String getNames() {
        StringBuilder stb = new StringBuilder();
        goods.forEach(position -> stb.append(position.getName()));
        return new String(stb);
    }

    public void setImmediatelyDelivery(boolean immediatelyDelivery) {
        this.immediatelyDelivery = immediatelyDelivery;
        if (this.immediatelyDelivery)
            goods.forEach(position -> position.setTypeOfGoods(TypeOfGoodsEnum.READY_CARGO.toString()));
        if (!this.immediatelyDelivery)
            goods.forEach(position -> position.setTypeOfGoods(TypeOfGoodsEnum.CONSOLIDATION_CARGO.toString()));
    }

    public void addGoods(Goods goods) {
        if (isNull(this.goods))
            this.goods = new ArrayList<>();

        this.goods.add(goods);
    }
}
