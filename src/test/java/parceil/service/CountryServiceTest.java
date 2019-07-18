package parceil.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceTest {

    @Autowired
    CountryService countryService;

    @Test
    public void checkEvent() {
        CountryService countryService = mock(CountryService.class);

        countryService.setEvent("Check event1");
        countryService.setEvent("Check event2");
        countryService.setEvent("Check event3");
        verify(countryService, times(3));
    }

}
