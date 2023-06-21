package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource(Map.of(
                "name", projectDTO.getName(), "description", projectDTO.getDescription())
        );

        jdbcTemplate.update("insert into Project (name, description) values (:name, :description)",
                parameterSource, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public int updateProject(ProjectDTO projectDTO) {
        return jdbcTemplate.update("update Project set name = :name, description = :description where id = :id",

                Map.of("name", projectDTO.getName(), "description", projectDTO.getDescription(), "id",
                        projectDTO.getId())
        );
    }

    public void deleteProject(Integer projectId) {
        jdbcTemplate.update("delete from Comment where comment.issueId in (select id from issue where PROJECTID = :projectId)",
                Map.of("projectId", projectId));

        jdbcTemplate.update("delete from Issue where PROJECTID = :projectId", Map.of("projectId", projectId));


        jdbcTemplate.update("delete from UserProject where projectid = :projectId", Map.of("projectId", projectId));

        jdbcTemplate.update("delete from Project where id = :projectId", Map.of("projectId", projectId));

    }

}
