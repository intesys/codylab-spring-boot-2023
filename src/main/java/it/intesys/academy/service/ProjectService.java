package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            // todo prepared statement
            resultSet = conn.createStatement().executeQuery("SELECT id, name, description FROM Project where id in (?)");

            while(resultSet.next()) {

                int projectId = resultSet.getInt("id");

                ProjectDTO projectDTO =
                        new ProjectDTO(projectId,
                                resultSet.getString("name"),
                                resultSet.getString("description"));


                projectList.add(projectDTO);
            }

            // projectIds
            List<Integer> projectIds = projectList.stream()
                    .map(ProjectDTO::getId)
                    .toList();

            //todo prepared statements
            resultSetIssues = conn.createStatement().executeQuery("SELECT id, name, description, author FROM Issue WHERE projectId in (?)");

            Map<Integer, List<IssueDTO>> issuesByProjectId = new HashMap<>();
            while (resultSetIssues.next()) {

                IssueDTO issueDTO = new IssueDTO();
                issueDTO.setId(resultSetIssues.getInt("id"));
                issueDTO.setName(resultSetIssues.getString("name"));
                issueDTO.setDescription(resultSetIssues.getString("description"));
                issueDTO.setAuthor(resultSetIssues.getString("author"));

                issuesByProjectId.get(resultSetIssues.getInt("projectId")).add(issueDTO);

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
