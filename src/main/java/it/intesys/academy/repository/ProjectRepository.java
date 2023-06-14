package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProjectDTO> readProjects(List<Integer> userProjectIds) {

        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                                                       Map.of("projectIds", userProjectIds),

                                                       BeanPropertyRowMapper.newInstance(ProjectDTO.class));

        return projects;
    }

    public ProjectDTO readProject(int projectId) {

        return jdbcTemplate.queryForObject("SELECT id, name, description FROM Projects where id = (:projectId)",

                                               Map.of("projectId", projectId),

                                               BeanPropertyRowMapper.newInstance(ProjectDTO.class));

    }

}