package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserProjectRepository {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public UserProjectRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserProjectDTO> searchUser(String username){
        return jdbcTemplate.query("SELECT id, username,projectId FROM USER_PROJECTS where username = (:username)",

                Map.of("username", username),

                BeanPropertyRowMapper.newInstance(UserProjectDTO.class));
    }
}
