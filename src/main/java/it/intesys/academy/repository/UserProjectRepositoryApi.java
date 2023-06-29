package it.intesys.academy.repository;

import it.intesys.academy.mockoon.client.MockoonApi;
import it.intesys.academy.mockoon.client.model.ProjectsMockoonDTO;
import org.springframework.stereotype.Repository;



@Repository
public class UserProjectRepositoryApi {

private final MockoonApi mockoonApi;

    public UserProjectRepositoryApi(MockoonApi mockoonApi) {
        this.mockoonApi = mockoonApi;
    }

    public boolean usernameProjectVisibility(String username, Integer projectId) {

        ProjectsMockoonDTO projectsMockoonDTO = mockoonApi.getProjectsForUsername(username);

        return projectsMockoonDTO.getProjects().stream().anyMatch(projectMockoonDTO -> projectMockoonDTO.getId() == projectId);

    }
/*
    public List<Integer> getUserProjects(String username) {

        return jdbcTemplate.queryForList("SELECT projectId FROM UserProject where username = :username",
                Map.of("username", username),
                Integer.class);
    }*/



}


