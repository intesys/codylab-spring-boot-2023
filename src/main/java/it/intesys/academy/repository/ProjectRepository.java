package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProjectDTO> readProjects(List<Integer> userProjectIds) {

        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Project where id in (:projectIds)",

                                                       Map.of("projectIds", userProjectIds),

                                                       BeanPropertyRowMapper.newInstance(ProjectDTO.class));

        return projects;
    }

    public ProjectDTO readProject(int projectId) {

        return jdbcTemplate.queryForObject("SELECT id, name, description FROM Project where id = (:projectId)",

                                               Map.of("projectId", projectId),

                                               BeanPropertyRowMapper.newInstance(ProjectDTO.class));

    }

    public Integer createProject(ProjectDTO projectDTO) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", projectDTO.getName())
                .addValue("description", projectDTO.getDescription()
                );
        int numberOfInsertedRows = jdbcTemplate.update("INSERT INTO Project (name, description) VALUES (:name, :description)",
                parameterSource, keyHolder
        );

        return keyHolder.getKey().intValue();
    }

    public void updateProject(ProjectDTO projectDTO) {
        jdbcTemplate.update("update Project set name = :name, description = :description where id = :projectId",
                Map.of("name", projectDTO.getName(),
                        "description", projectDTO.getDescription(),
                        "projectId", projectDTO.getId()
                ));
    }

    public void deleteProject(Integer projectId) {

        jdbcTemplate.update("delete from Project where id = :projectId", Map.of("projectId", projectId));

    }

}
