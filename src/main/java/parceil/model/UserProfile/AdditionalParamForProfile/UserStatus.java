package parceil.model.UserProfile.AdditionalParamForProfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import parceil.enums.UserProfile.StatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {

    StatusEnum userStatus = StatusEnum.AWAITING_FILES;
}
