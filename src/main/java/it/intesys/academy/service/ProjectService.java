package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
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
                (resultSet, rowNum) ->
                        new ProjectDTO(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"))
        );

        List<Integer> projectIds = projects.stream()
                .map(ProjectDTO::getId)
                .toList();

        Map<Integer, List<IssueDTO>> issuesByProjectId = new HashMap<>();
        jdbcTemplate.query("SELECT id, name, description, author, projectId FROM Issue WHERE projectId in (:projectIds)",
                new MapSqlParameterSource("projectIds", projectIds),
                resultSet -> {
                    IssueDTO issueDTO = new IssueDTO();
                    issueDTO.setId(resultSet.getInt("id"));
                    issueDTO.setName(resultSet.getString("name"));
                    issueDTO.setDescription(resultSet.getString("description"));
                    issueDTO.setAuthor(resultSet.getString("author"));

                    // building map projectId --> [issue1, issue2, issue3]
                    int projectId = resultSet.getInt("projectId");
                    if (!issuesByProjectId.containsKey(projectId)) {
                        issuesByProjectId.put(projectId, new ArrayList<>());
                    }

                    issuesByProjectId.get(projectId).add(issueDTO);
                }
        );


        for (ProjectDTO dto : projects) {
            List<IssueDTO> issueDTOS = issuesByProjectId.get(dto.getId());
            issueDTOS.forEach(dto::addIssue);
        }

        return projects;

    }

}
