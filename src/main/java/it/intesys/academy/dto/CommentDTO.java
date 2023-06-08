package it.intesys.academy.dto;

public class CommentDTO {
    private int id;
    private String descrizione;
    private String author;

    public int getId() {
        return id;
    }

    public CommentDTO(int id, String descrizione, String author) {
        this.id = id;
        this.descrizione = descrizione;
        this.author = author;
    }

    public CommentDTO() {
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

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
