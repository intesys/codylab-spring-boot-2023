package it.intesys.academy.dto;

import java.util.List;

public class IssueDTO {

    private int id;

    private int projectId;

    private String name;

    private String description;

    private String author;

    private List<CommentDTO> comments;

    public int getProjectId() {

        return projectId;
    }

    public void setProjectId(int projectId) {

        this.projectId = projectId;
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

    public List<CommentDTO> getComments() {

        return comments;
    }

    public void setComments(List<CommentDTO> comments) {

        this.comments = comments;
    }
}
