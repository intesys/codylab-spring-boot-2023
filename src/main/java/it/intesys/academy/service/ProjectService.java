package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;
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

        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                                                       Map.of("projectIds", userProjects),

                                                       BeanPropertyRowMapper.newInstance(ProjectDTO.class));


        List<Integer> projectIds = projects.stream()
                                           .map(ProjectDTO::getId)
                                           .toList();

        Map<Integer, List<IssueDTO>> issuesByProjectId = new HashMap<>();
        List<Integer> issueIds = new ArrayList<>();

        jdbcTemplate.query("SELECT id, name, message, author, projectId FROM Issues WHERE projectId in (:projectIds)",

                           Map.of("projectIds", projectIds),

                            (resultSet) -> {

                                IssueDTO issueDTO = new IssueDTO();
                                issueDTO.setId(resultSet.getInt("id"));
                                issueIds.add(resultSet.getInt("id"));
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
        log.info(issueIds.toString());

        Map<Integer, List<MessageDTO>> messageByIssuetId = new HashMap<>();
        jdbcTemplate.query("SELECT id, comment, author, issueId FROM Comments WHERE issueId in (:issueIds)",
                Map.of("issueIds", issueIds),

                (resultSet) -> {
                    MessageDTO messageDTO = new MessageDTO();


                    messageDTO.setComment(resultSet.getString("comment"));
                    log.info(resultSet.getString("comment"));
                    log.info(messageDTO.getComment());
                    int issueID = resultSet.getInt("issueId");
                    if(Boolean.FALSE.equals(messageByIssuetId.containsKey(issueID))){
                        messageByIssuetId.put(issueID, new ArrayList<>());
                    }
                    messageByIssuetId.get(issueID).add(messageDTO);


                }
                );




        for (ProjectDTO dto : projects) {
            List<IssueDTO> issueDTOS = issuesByProjectId.get(dto.getId());
            for (IssueDTO issue : issueDTOS){
                List<MessageDTO> messageDTOS = messageByIssuetId.get(issue.getId());
                for (MessageDTO message : messageDTOS) {
                    issue.setComment(message);
                }
                dto.addIssue(issue);
            }
        }


        return projects;

    }

}
