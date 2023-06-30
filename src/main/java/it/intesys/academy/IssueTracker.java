package it.intesys.academy;

import it.intesys.academy.mockoon.client.MockoonApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class IssueTracker {

    private static final Logger log = LoggerFactory.getLogger(IssueTracker.class);

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public MockoonApi mockoonApi(){
        MockoonApi mockoonApi =  new MockoonApi();
        mockoonApi.getApiClient().setBasePath("http://localhost:3003/api/v1");
        return mockoonApi;
    }


    public static void main(String[] args) {

        SpringApplication.run(IssueTracker.class, args);

    }

}
