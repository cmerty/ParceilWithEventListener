package parceil.model.Stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LittleBox {

    private double maxWeight;

    private double lengthWidthHeight;

    public void setLengthWidthHeight(double length, double width, double height) {
        this.lengthWidthHeight = length * width * height;
    }
}
