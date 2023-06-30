package it.intesys.academy.configuration;

import it.intesys.academy.mockoon.client.MockoonApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MockoonApiConfiguration {
    @Bean
    public MockoonApi mockoonApi(@Value("${app.mockoon}")String mockoon){
        MockoonApi mockoonApi =  new MockoonApi();
        mockoonApi.getApiClient().setBasePath(mockoon);
        return mockoonApi;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
