package parceil.model.UserProfile;

import lombok.Getter;
import lombok.Setter;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DirectoryOfRecipientCountries {

    private List<Country> countries = new ArrayList<>();

}

