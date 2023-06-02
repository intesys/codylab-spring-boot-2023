package it.intesys.academy.dto;

import java.util.List;

public class CommentsDTO {
    private int id;
    private String comment;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public CommentsDTO(int id, String comment, String author) {
        this.id = id;
        this.comment = comment;
        this.author = author;
    }

}
