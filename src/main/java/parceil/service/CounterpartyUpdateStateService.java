package parceil.service;


import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import parceil.enums.UserProfile.StatusEnum;
import parceil.model.UserProfile.AdditionalParamForProfile.Document;
import parceil.model.UserProfile.AdditionalParamForProfile.UserStatus;
import parceil.model.UserProfile.UserProfile;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CounterpartyUpdateStateService {

    public List<UserProfile> userRepository;

    public void setUserRepository(List<UserProfile> userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(CountryChangeDocumentEvent.class)
    public void updateCounterpartyState(CountryChangeDocumentEvent event) {

        Long id = event.getId();

        List<UserProfile> userFromCountry = userRepository.stream().filter(user -> user.getCountry().getId().equals(id)).collect(toList());

        userFromCountry.forEach(user -> {

            List<Document> userDocument = user.getDocumentList();

            List<String> requiredDocument = user.getDirectoryOfRecipientCountries()
                    .getCountries()
                    .stream()
                    .filter(country -> country.getId().equals(id))
                    .findFirst().orElseThrow(() -> new RuntimeException("Country has not found"))
                    .getRequiredDocument();

            if (!userDocument.containsAll(requiredDocument))
                user.setUserStatus(new UserStatus(StatusEnum.AWAITING_FILES));
            else
                user.setUserStatus(new UserStatus(StatusEnum.AWAITING_VERIFICATION));
        });
    }
}

