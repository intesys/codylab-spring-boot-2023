package it.intesys.academy.dto;

public class CommentDTO {
    private Integer id;

    private String descrizione;

    private String author;

    private Integer issueId;

    public CommentDTO() {
    }

    public CommentDTO(Integer id, String descrizione, String author, Integer issueId) {
        this.id = id;
        this.descrizione = descrizione;
        this.author = author;
        this.issueId = issueId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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

    public String getDescrizione() {
        return descrizione;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getIssueId() {
        return issueId;
    }
}
