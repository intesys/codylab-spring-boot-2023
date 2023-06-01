package it.intesys.academy.dto;

public class IssueDTO {
    private int id;
    private String name;
    private String descrizione;
    private String author;

    public IssueDTO(int id, String name, String descrizione, String author) {
        this.id = id;
        this.name = name;
        this.descrizione = descrizione;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
