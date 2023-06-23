package it.intesys.academy.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Issue")
public class Issue {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;


    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="author")
    private String author;

    @ManyToOne
    @JoinColumn(name="projectid")
    private Project project;
    @OneToMany(mappedBy = "issue")
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
