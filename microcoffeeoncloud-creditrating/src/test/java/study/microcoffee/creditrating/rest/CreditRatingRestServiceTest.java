package study.microcoffee.creditrating.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.microcoffee.creditrating.behavior.ServiceBehaviorFactory;
import study.microcoffee.creditrating.domain.CreditRating;

/**
 * Unit tests of {@link CreditRatingRestService}.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CreditRatingRestService.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@TestPropertySource("/application-test.properties")
public class CreditRatingRestServiceTest {

    private static final String SERVICE_PATH = "/coffeeshop/creditrating/{customerId}";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getCreditRatingShouldReturnRating() throws Exception {
        final String expectedContent = objectMapper.writeValueAsString(new CreditRating(70));

        mockMvc.perform(get(SERVICE_PATH, 123).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)) //
            .andExpect(status().isOk()) //
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) //
            .andExpect(content().json(expectedContent));
    }

    @TestConfiguration
    static class Config {

        @Bean
        public ServiceBehaviorFactory serviceBehaviorFactory() {
            return new ServiceBehaviorFactory();
        }
    }
}
