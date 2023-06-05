package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static it.intesys.academy.IssueTracker.log;

public class ProjectService {
    public MessageDTO testConnection(){
        MessageDTO message = new MessageDTO();
        message.setTimestamp(new Date());

        Connection conn = DatabaseManager.getConnection();

        if(conn != null){
            message.setText("Connession correctly taken!");
            DatabaseManager.closeConnection(conn);
        }
        else{
            message.setText("Oooops we have some problem!");
        }

        return message;
    }
    public List<ProjectDTO> readProjects(){

        List<ProjectDTO> projectList = new ArrayList<>();
        Connection conn = DatabaseManager.getConnection();
        Statement statementProject = null;
        Statement statementIssue = null;
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        try{
            statementProject = conn.createStatement();
            resultSet =  statementProject.executeQuery("SELECT id, name, description FROM Projects");

            statementIssue = conn.createStatement();
           while(resultSet.next()){
                int projectId = resultSet.getInt("id");
                ProjectDTO projectDTO = new ProjectDTO(
                       projectId,
                       resultSet.getString("name"),
                       resultSet.getString("description")
               );

               resultSet1 = statementIssue.executeQuery("SELECT id, name, message, project_id, author FROM Issues WHERE project_id = " + projectId);
               projectList.add(projectDTO);

                while(resultSet1.next()){
                    projectDTO.addIssue( new IssueDTO(
                        resultSet1.getInt("id"),
                            resultSet1.getString("name"),
                            resultSet1.getString("message"),
                            resultSet1.getString("author"),
                            resultSet1.getInt("project_id")
                    ));

                }

           }


            return projectList;

        }
        catch(SQLException e){
            DatabaseManager.closeConnection(conn);
            log.error("SQLException" , e);
        }
        catch(Exception e ){
            log.error("Exception" , e);
        }
        finally{
            DatabaseManager.closeConnection(conn);
            DatabaseManager.closeStatement(statementProject);
            DatabaseManager.closeStatement(statementIssue);
            DatabaseManager.closeResult(resultSet);
            DatabaseManager.closeResult(resultSet1);
        }
        return projectList;
    }
};

