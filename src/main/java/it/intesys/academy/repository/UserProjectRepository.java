package it.intesys.academy.repository;

import it.intesys.academy.dto.UserProjectDTO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserProjectRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserProjectDTO> usernameProjectVisibility(String username, Integer projectId) {

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

    public List<Integer> getUserProjects(String username) {

        return jdbcTemplate.queryForList("SELECT projectId FROM UserProject where username = :username",
                Map.of("username", username),
                Integer.class);
    }

    public void inserUserProject(String username, Integer projectId) {
        jdbcTemplate.update("insert into UserProject (username, projectId) values (:username, :projectId)",
                Map.of("username", username, "projectId", projectId));

    }

}
