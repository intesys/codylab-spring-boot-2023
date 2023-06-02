package it.intesys.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class IssueDTO {
    private int id;
    private String name;
    private String description;
    private String author;
    private List<CommentsDTO> commentsList = new ArrayList<>();


    public IssueDTO(int id, String name, String description, String author) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public void addComments(CommentsDTO comment){
        commentsList.add(comment);
    }
    public List<CommentsDTO> getComments(){
        return commentsList;
    }
}
