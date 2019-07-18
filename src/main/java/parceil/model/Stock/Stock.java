package parceil.model.Stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    private String name;

    private String country;

    private String address;

    private String email;

    private String telephoneNumber;

    private String nameForDocumentation;

    private String fullNameOfResponsiblePerson;

    private double coefficient;
    //Ceil accounting
    private String currency;

    private boolean isContainBatteriesLiquidsOrPowders;

    private boolean displayInTheCabinet;

    private List<Integer> userAccess;

    private boolean buyoutBan;
    //cell selection mode
    private int daysOfFreeStorage;

    private String typeOfDelivery;

    private String prefix;

    private String suffix;

    private LittleBox littleBox;
    //Table of tracking number
}
