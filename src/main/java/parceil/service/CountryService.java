package parceil.service;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import parceil.model.UserProfile.AdditionalParamDirectoryOfRecipientCountries.Country;

import java.util.List;

@Service
public class CountryService {

    private final ApplicationEventPublisher publisher;

    private List<Country> countries;

    public CountryService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void addDocument(Long id, String document) {
        Country country = countries.stream().filter(requiredCountry -> requiredCountry.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Country has not found"));
        country.addDocument(document);
        CountryChangeDocumentEvent countryChangeDocumentEvent = CountryChangeDocumentEvent.countryIdAndDocumentEvent(id, document);
        publisher.publishEvent(countryChangeDocumentEvent);
    }

    public void setEvent(String check_event1) {
        System.out.println(check_event1);

    }
}
