package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectService {
    static Logger log = LoggerFactory.getLogger(ProjectService.class);

    public MessageDTO testConnection(){
        MessageDTO message = new MessageDTO();
        message.setTimestamp(new Date());
        Connection conn = new DatabaseManager().getConnection();
        if (conn != null){
            message.setText("Connection activated!");
            DatabaseManager.closeConnections(conn);
        }else{
            message.setText("Missing connection :(");
        }
        return message;
    }

    public static List<ProjectDTO> readProject(){
        List<ProjectDTO> projectsList = new ArrayList<ProjectDTO>();
        Connection conn = null;
        Connection stm = null;

        try {
            conn = DatabaseManager.getConnection();
            ResultSet projectSelection = conn.createStatement().executeQuery("SELECT id, name, description FROM Projects");
            while(projectSelection.next()) {
                int projectID = projectSelection.getInt("ID");
                ProjectDTO project = new ProjectDTO(projectID, projectSelection.getString("name"), projectSelection.getString("description"));
                List<IssueDTO> issuesList = new ArrayList<IssueDTO>();
                stm = DatabaseManager.getConnection();
                ResultSet issuesSelection = stm.createStatement().executeQuery("SELECT id, name, message, author FROM Issues WHERE PROJECTID = " + projectID);

                while (issuesSelection.next()) {
                    IssueDTO issue = new IssueDTO(issuesSelection.getInt("ID"), issuesSelection.getString("name"), issuesSelection.getString("message"), issuesSelection.getString("author"));
                    project.setIssues(issue);
                }
                projectsList.add(project);
            }
            return projectsList;
        }
        catch (Exception error){
            DatabaseManager.closeConnections(conn);
            DatabaseManager.closeConnections(stm);
            log.error("there aren't any table with this name", error);
        }
        finally {
            DatabaseManager.closeConnections(conn);
            DatabaseManager.closeConnections(stm);

        }
        return projectsList;
    }
}
