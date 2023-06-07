package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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

        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                                                       Map.of("projectIds", userProjects),

                                                       BeanPropertyRowMapper.newInstance(ProjectDTO.class));


        List<Integer> projectIds = projects.stream()
                                           .map(ProjectDTO::getId)
                                           .toList();

        Map<Integer, List<IssueDTO>> issuesByProjectId = new HashMap<>();

        jdbcTemplate.query("SELECT id, name, message, author, projectId FROM Issues WHERE projectId in (:projectIds)",

                           Map.of("projectIds", projectIds),

                            (resultSet) -> {

                                IssueDTO issueDTO = new IssueDTO();
                                issueDTO.setId(resultSet.getInt("id"));
                                issueDTO.setName(resultSet.getString("name"));
                                issueDTO.setMessage(resultSet.getString("message"));
                                issueDTO.setAuthor(resultSet.getString("author"));

                                // building map projectId --> [issue1, issue2, issue3]
                                int projectId = resultSet.getInt("projectId");
                                if ( Boolean.FALSE.equals(issuesByProjectId.containsKey(projectId)) ) {
                                    issuesByProjectId.put(projectId, new ArrayList<>());
                                }

                                issuesByProjectId.get(projectId).add(issueDTO);
                          });

        for (ProjectDTO dto : projects) {
            List<IssueDTO> issueDTOS = issuesByProjectId.get(dto.getId());
            issueDTOS.forEach(dto::addIssue);
        }


        return projects;

    }

}
