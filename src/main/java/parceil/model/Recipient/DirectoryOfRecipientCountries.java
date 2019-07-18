package parceil.model.Recipient;

import lombok.Getter;
import lombok.Setter;
import parceil.model.Recipient.AdditionalParamDirectoryOfRecipientCountries.AdditionalParamForRecipientForm;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DirectoryOfRecipientCountries {

    private List<Country> countries = new ArrayList<>();

    private AdditionalParamForRecipientForm additionalParamForRecipientForm = new AdditionalParamForRecipientForm();
}
