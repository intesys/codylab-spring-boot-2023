package it.intesys.academy.dto;

public class CommentDTO {
    private Integer id;

    private String description;

    private String author;

    private Integer issueId;

    public CommentDTO() {
    }

    public CommentDTO(Integer id, String description, String author, Integer issueId) {
        this.id = id;
        this.description = description;
        this.author = author;
        this.issueId = issueId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getIssueId() {
        return issueId;
    }
}
