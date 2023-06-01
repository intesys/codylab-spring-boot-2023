package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        try{
           ResultSet resultSet =  conn.createStatement().executeQuery("SELECT id, name, description FROM Projects");
           ProjectDTO projectDTO = null;
           while(resultSet.next()){

                projectDTO = new ProjectDTO(
                       resultSet.getInt("id"),
                       resultSet.getString("name"),
                       resultSet.getString("description")
               );

               projectList.add(projectDTO);

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
        }
        return projectList;
    }
};

