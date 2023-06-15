package it.intesys.academy.repository;

import it.intesys.academy.dto.UserProjectDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@Repository
public class UserProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserProjectRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserProjectDTO> usernameProjectVisibility(String username, Integer projectId) {

        UserProjectDTO project = jdbcTemplate.queryForObject("SELECT id FROM User_Projects where projectId = (:projectId) and username = (:username)",

                                                                Map.of("projectId", projectId, "username", username),

                                                                BeanPropertyRowMapper.newInstance(UserProjectDTO.class));

        return Optional.ofNullable(project);
    }

    public List<UserProjectDTO> getUserProjects(String username) {

        List<UserProjectDTO> projects = jdbcTemplate.query("SELECT id, username, projectId FROM User_Projects where username = (:username)",
                Map.of("username", username),
                BeanPropertyRowMapper.newInstance(UserProjectDTO.class));

        return projects;
    }

    public void insertUserProject(Integer projectId, String userName){
        jdbcTemplate.update("INSERT INTO User_Projects (username, projectId) Values (:username,:projectId)",
                Map.of("username",userName, "projectId", projectId));
    }

}
