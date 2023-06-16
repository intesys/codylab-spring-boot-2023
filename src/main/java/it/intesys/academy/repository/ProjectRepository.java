package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public Integer createProject(ProjectDTO projectDTO){
        //Map<String, String> project = Map.of("name",projectDTO.getName(),"description",projectDTO.getDescription());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", projectDTO.getName())
                .addValue("description", projectDTO.getDescription());
        jdbcTemplate.update("INSERT INTO PROJECTS (name,description) VALUES (:name,:description)",
                parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void updateRepository(ProjectDTO projectDTO){
        jdbcTemplate.update("UPDATE PROJECTS SET name = :name, description = :description where id = :projectId",
                Map.of("name",projectDTO.getName(),
                        "description",projectDTO.getDescription(),
                        "projectId", projectDTO.getId()));
    }

    public void deleteProject(Integer projectId){
        jdbcTemplate.update("Delete FROM Projects where id = :projectId",
                Map.of("projectId",projectId));
    }

}
