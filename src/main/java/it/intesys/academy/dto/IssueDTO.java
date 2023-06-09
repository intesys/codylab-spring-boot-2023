package it.intesys.academy.dto;

public class IssueDTO {

    private int id;

    private String nome;

    private String descrizione;

    private String author;

    private int projectId;

    public IssueDTO(int id, String nome, String descrizione, String author, int projectId) {
        this.id = id;
        this.nome = nome;
        this.descrizione= descrizione;
        this.author = author;
        this.projectId= projectId;
    }

    public IssueDTO() {
    }

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

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getDescrizione() {

        return descrizione;
    }

    public void setDescrizione(String descrizione) {

        this.descrizione = descrizione;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }
}
