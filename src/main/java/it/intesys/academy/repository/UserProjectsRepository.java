package it.intesys.academy.repository;

import it.intesys.academy.domain.UserProject;
import it.intesys.academy.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
public class UserProjectsRepository {

    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserProjectsRepository.class);

    public UserProjectsRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<UserProject> usernameProjectVisibility(String username, Integer projectId){
        try{
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:3003/api/v1/projects/"+username, String.class);
            log.info("Status Code "+response.getStatusCode());
            log.info("Body "+response.getBody());
            return Optional.empty();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
