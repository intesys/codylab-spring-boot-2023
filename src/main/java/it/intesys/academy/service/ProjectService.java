package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;

    public ProjectService(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
    }


    public List<ProjectDTO> readProjects(String username) {

        List<Integer> userProjects = settingsService.getUserProjects(username);

        List<ProjectDTO> projects = jdbcTemplate.query(
                "SELECT id, name, description FROM Project where id in (:projectIds)",
                Map.of("projectIds", userProjects),
                new BeanPropertyRowMapper<>()
        );

        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();

        List<IssueDTO> issues = jdbcTemplate.query(
                "SELECT id, name, description, author, projectId FROM Issue WHERE projectId in (:projectIds)",
                Map.of("projectIds", projectIds),
                new BeanPropertyRowMapper<>()
        );

        // group issues by project id
        Map<Integer, List<IssueDTO>> issuesByProjectId = issues.stream()
                .collect(Collectors.groupingBy(IssueDTO::getProjectId));

        // add issues to project
        for (ProjectDTO dto : projects) {
            List<IssueDTO> issueDTOS = issuesByProjectId.get(dto.getId());
            issueDTOS.forEach(dto::addIssue);
        }


        return projects;

    }

}
