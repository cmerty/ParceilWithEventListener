package parceil.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryChangeDocumentEvent {

    private Long id;

    private String document;

    private CountryChangeDocumentEvent(Long id, String country) {
        this.id = id;
        this.document = country;
    }

    public static CountryChangeDocumentEvent countryIdAndDocumentEvent(Long id, String document) {
        return new CountryChangeDocumentEvent(id, document);
    }
}
