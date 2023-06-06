package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final DataSource dataSource;

    private final SettingsService settingsService;

    public ProjectService(DataSource dataSource, SettingsService settingsService) {
        this.dataSource = dataSource;
        this.settingsService = settingsService;
    }


    public List<ProjectDTO> readProjects(String username) {

        List<Integer> userProjects = settingsService.getUserProjects(username);

        List<ProjectDTO> projectList = new ArrayList<>();
        ResultSet resultSet = null;
        ResultSet resultSetIssues = null;

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement psProject = conn.prepareStatement("SELECT id, name, description FROM Project where id = ANY (?)");
            psProject.setObject(1, userProjects.toArray());

            resultSet = psProject.executeQuery();

            while(resultSet.next()) {

                int projectId = resultSet.getInt("id");

                ProjectDTO projectDTO =
                        new ProjectDTO(projectId,
                                resultSet.getString("name"),
                                resultSet.getString("description"));


                projectList.add(projectDTO);
            }

            // projectIds converted to a list of
            List<Integer> projectIds = projectList.stream()
                    .map(ProjectDTO::getId)
                    .toList();

            // We deal with Issues now
            PreparedStatement psIssues = conn.prepareStatement("SELECT id, name, description, author, projectId FROM Issue WHERE projectId = ANY (?)");
            psIssues.setObject(1, projectIds.toArray());

            resultSetIssues = psIssues.executeQuery();

            Map<Integer, List<IssueDTO>> issuesByProjectId = new HashMap<>();

            while (resultSetIssues.next()) {

                IssueDTO issueDTO = new IssueDTO();
                issueDTO.setId(resultSetIssues.getInt("id"));
                issueDTO.setName(resultSetIssues.getString("name"));
                issueDTO.setDescription(resultSetIssues.getString("description"));
                issueDTO.setAuthor(resultSetIssues.getString("author"));

                int projectId = resultSetIssues.getInt("projectId");
                if (issuesByProjectId.containsKey(projectId)) {
                    issuesByProjectId.get(projectId).add(issueDTO);
                }
                else {
                    issuesByProjectId.put(projectId, Stream.of(issueDTO).collect(Collectors.toList()));
                }

            }

            for (ProjectDTO dto : projectList) {
                List<IssueDTO> issueDTOS = issuesByProjectId.get(dto.getId());
                issueDTOS.forEach(dto::addIssue);
            }

            return projectList;
        }
        catch (SQLException e) {
            log.error("SQLException", e);
        }
        catch (Exception e) {
            log.error("Exception", e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeResultSet(resultSetIssues);
        }

        return projectList;

    }

}
