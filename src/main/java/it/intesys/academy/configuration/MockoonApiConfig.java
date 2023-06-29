package it.intesys.academy.configuration;

import it.intesys.academy.mockoon.client.MockoonApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockoonApiConfig {
    @Bean
    public MockoonApi mockoonApi(@Value("${app.mockoon}") String mockoonUrl) {

        MockoonApi mockoonApi = new MockoonApi();

        mockoonApi.getApiClient().setBasePath( mockoonUrl );

        return mockoonApi;
    }
}
