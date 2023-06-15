package it.intesys.academy.repository;

import it.intesys.academy.dto.UserProjectDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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

        try {
            UserProjectDTO project = jdbcTemplate.queryForObject("SELECT id FROM UserProject where projectId = (:projectId) and username = (:username)",

                    Map.of("projectId", projectId, "username", username),

                    BeanPropertyRowMapper.newInstance(UserProjectDTO.class));

            return Optional.ofNullable(project);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
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
