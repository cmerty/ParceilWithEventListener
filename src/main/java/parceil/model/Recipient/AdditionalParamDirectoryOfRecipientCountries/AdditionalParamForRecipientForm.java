package parceil.model.Recipient.AdditionalParamDirectoryOfRecipientCountries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalParamForRecipientForm {

    private boolean isPassportsSeriesRequired;

    private boolean isPassportNumberRequired;

    private boolean isDateOfIssuedRequired;

    private boolean isPlaceOfIssuedRequired;

    private boolean isITNRequired;
}
