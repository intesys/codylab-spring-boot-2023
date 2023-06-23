package it.intesys.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class IssueDTO {

    private Integer id;
    private String name;

    private String description;

    private String author;

    private int projectId;

    private List<CommentDTO> comments = new ArrayList<>();

    public int getProjectId() {

        return projectId;
    }

    public void setProjectId(int projectId) {

        this.projectId = projectId;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

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

    public void addComment(CommentDTO commentDTO){

        this.comments.add(commentDTO);
    }

    public void setComments(List<CommentDTO> comments) {

        this.comments = comments;
    }
}
