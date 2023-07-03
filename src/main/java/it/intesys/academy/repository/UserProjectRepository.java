package it.intesys.academy.repository;

import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.mockoon.client.MockoonApi;
import it.intesys.academy.mockoon.client.model.ProjectsMockoonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserProjectRepository {

    private static final Logger log = LoggerFactory.getLogger(UserProjectRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final MockoonApi mockoonApi;

    public UserProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, MockoonApi mockoonApi) {

        this.jdbcTemplate = jdbcTemplate;
        this.mockoonApi = mockoonApi;
    }

    public boolean usernameProjectVisibility(String username, Integer projectId) {

        ProjectsMockoonDTO projectsMockoonDTO = mockoonApi.getProjectsForUsername(username);

        return projectsMockoonDTO.getProjects().stream().anyMatch(projectMockoonDTO -> projectMockoonDTO.getId() == projectId);

    }

    public boolean usernameProjectVisibility2(String username, Integer projectId){
        List<Integer> projectId_ = jdbcTemplate.queryForList("SELECT projectId FROM UserProject where username = :username",
                Map.of("username", username),
                Integer.class);
            return projectId_.contains(projectId);
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
