package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ProjectService {
    public static MessageDTO testConnection(){
        MessageDTO message = new MessageDTO();
        message.setTimestamp(new Date());

        Connection conn = DatabaseManager.getConnection();

        if(conn != null){
            message.setText("Connection");
            DatabaseManager.closeConnection(conn);
        }else{
            message.setText("Connection failure");
        }

        return message;
    }

    public static List<ProjectDTO> readProject(){
        Connection conn =null;
        List<ProjectDTO> lista = new ArrayList<>();
        Statement stmt=null;
        ResultSet set = null;
        try{
            conn = DatabaseManager.getConnection();
            stmt = conn.createStatement();
            set = stmt.executeQuery("SELECT id,name,description FROM Projects");

            while (set.next()){
                lista.add(new ProjectDTO(set.getInt("id"), set.getString("name"), set.getString("description")));
            }
        }catch (Exception e){
            DatabaseManager.closeConnection(conn);
        }finally {
            DatabaseManager.closeConnection(conn);
            DatabaseManager.closeStatement(stmt);
            DatabaseManager.closeResultSet(set);
        }
        return lista;
    }

    public static List<IssueDTO> readIssue(){
        Connection conn =null;
        List<IssueDTO> lista = new ArrayList<>();
        Statement stmt=null;
        ResultSet set = null;
        try{
            conn = DatabaseManager.getConnection();
            stmt = conn.createStatement();
            set = stmt.executeQuery("SELECT id,nome,descrizione,author FROM Issues ");

            while (set.next()){
                lista.add(new IssueDTO(set.getInt("id"), set.getString("nome"), set.getString("descrizione"), set.getString("author")));
            }

        }catch (Exception e){
            DatabaseManager.closeConnection(conn);
        }finally {
            DatabaseManager.closeConnection(conn);
            DatabaseManager.closeStatement(stmt);
            DatabaseManager.closeResultSet(set);
        }
        return lista;
    }

    public static List<ProjectDTO> readIssueForProject(){
        Connection conn =null;
        List<ProjectDTO> lista = new ArrayList<>();
        Statement stmtProject=null;
        ResultSet setProject = null;
        Statement stmtIssue=null;
        ResultSet setIssue = null;
        try{
            conn = DatabaseManager.getConnection();
            stmtProject = conn.createStatement();
            stmtIssue = conn.createStatement();
            setProject = stmtProject.executeQuery("SELECT id,name,description FROM Projects");


            while (setProject.next()){
                int projectID = setProject.getInt("id");
                ProjectDTO project = (new ProjectDTO(projectID, setProject.getString("name"), setProject.getString("description")));
                lista.add(project);
                setIssue = stmtIssue.executeQuery("SELECT id,nome,descrizione,author FROM Issues WHERE projectId = "+projectID);
                while(setIssue.next()){
                    project.addIssue( new IssueDTO(setIssue.getInt("id"),setIssue.getString("nome"),setIssue.getString("descrizione"),setIssue.getString("author")));
                }
            }


        }catch (Exception e){
            DatabaseManager.closeConnection(conn);
        }finally {
            DatabaseManager.closeConnection(conn);
            DatabaseManager.closeStatement(stmtIssue);
            DatabaseManager.closeResultSet(setIssue);
            DatabaseManager.closeResultSet(setProject);
            DatabaseManager.closeStatement(stmtProject);
        }
        return lista;
    }


}
