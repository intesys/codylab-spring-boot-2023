package it.intesys.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class IssueDTO {

    private int id;

    private String name;

    private String message;

    private String author;
    private List<MessageDTO> comment = new ArrayList<>();

    public List<MessageDTO> getComment() {
        return comment;
    }

    public void setComment(MessageDTO comment) {
        this.comment.add(comment);
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

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }
}
