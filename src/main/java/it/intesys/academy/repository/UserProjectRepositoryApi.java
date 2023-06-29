package it.intesys.academy.repository;

import it.intesys.academy.domain.UserProject;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Repository
public class UserProjectRepositoryApi {

    public Optional<UserProject> usernameProjectVisibility(String username, Integer projectId) {

        //Add http client
        try {
            HttpRequest request =
                    HttpRequest.newBuilder().uri(new URI("http://localhost:3003/api/v1/projects/" + username))
                            .GET()
                            .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            response.body();

            //Check id project is into the list with projectId
            return Optional.empty();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
/*
    public List<Integer> getUserProjects(String username) {

        return jdbcTemplate.queryForList("SELECT projectId FROM UserProject where username = :username",
                Map.of("username", username),
                Integer.class);
    }*/



}


