package it.intesys.academy.dto;

public class IssueDTO {

    public int id;

    public IssueDTO(int id, String name, String message, String author, int project_id) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.author = author;
        this.project_id = project_id;
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

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String name;
    public String message;
    public String author;

    public int project_id;
}
