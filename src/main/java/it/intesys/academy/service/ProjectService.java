package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    public MessageDTO testConnection() {

        MessageDTO message = new MessageDTO();
        message.setTimestamp(new Date());

        Connection conn = DatabaseManager.getConnection();

        if (conn != null) {

            message.setText("Connection taken correctly!");
            DatabaseManager.closeConnection(conn);

        }
        else {

            message.setText("Attention! We have some problems!");

        }

        return message;

    }

    public List<ProjectDTO> readProjects() {

        List<ProjectDTO> projectList = new ArrayList<>();

        Connection conn = null;
        ResultSet resultSet = null;
        ResultSet resultSetIssues = null;

        try {

            conn = DatabaseManager.getConnection();

            resultSet = conn.createStatement().executeQuery("SELECT id, name, description FROM Project");

            while(resultSet.next()) {

                int projectId = resultSet.getInt("id");

                ProjectDTO projectDTO =
                    new ProjectDTO( projectId,
                                    resultSet.getString("name"),
                                    resultSet.getString("description"));

                projectList.add(projectDTO);

                resultSetIssues = conn.createStatement().executeQuery("SELECT id, name, description, author FROM Issue WHERE projectId = " + projectId);

                while (resultSetIssues.next()) {

                    IssueDTO issueDTO = new IssueDTO();
                    issueDTO.setId(resultSetIssues.getInt("id"));
                    issueDTO.setName(resultSetIssues.getString("name"));
                    issueDTO.setDescription(resultSetIssues.getString("description"));
                    issueDTO.setAuthor(resultSetIssues.getString("author"));

                    projectDTO.addIssue(issueDTO);

                }

            }

            return projectList;

        }
        catch (SQLException e) {
            log.error("SQLException", e);
        }
        catch (Exception e) {
            log.error("Exception", e);
        }
        finally {

            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeResultSet(resultSetIssues);
            DatabaseManager.closeConnection(conn);

        }

        return projectList;

    }

}
