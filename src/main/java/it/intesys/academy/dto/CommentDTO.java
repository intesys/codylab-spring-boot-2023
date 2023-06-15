package it.intesys.academy.dto;

public class CommentDTO {

    private Integer id;

    private String text;

    private String author;

    private Integer issueId;


    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId (Integer issueId) {
        this.issueId = issueId;
    }
}
