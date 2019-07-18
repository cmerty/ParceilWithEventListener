package parceil.model.UserProfile.AdditionalParamForProfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {

    private String name;

    private Type type;

    private byte[] file;

    private DocumentStatus documentStatus = new DocumentStatus();
}
