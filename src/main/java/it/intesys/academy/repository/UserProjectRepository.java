package it.intesys.academy.repository;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.mockoon.client.MockoonApi;
import it.intesys.academy.mockoon.client.model.ProjectsMockoonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserProjectRepository {

    private static final Logger log = LoggerFactory.getLogger(UserProjectRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final MockoonApi mockoonApi;

    public UserProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, MockoonApi mockoonApi) {

        this.jdbcTemplate = jdbcTemplate;
        this.mockoonApi = mockoonApi;
    }

    public Optional<UserProjectDTO> usernameProjectVisibility(String username, Integer projectId) {

        //Add http client
        try {

            ProjectsMockoonDTO projectsMockoonDTO = mockoonApi.getProjectsForUsername(username);

            log.info("projectsMockoonDTO" + projectsMockoonDTO);
        }
        catch (Exception e) {

            log.error("Exception occurs ", e);

        }

        return Optional.empty();

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
