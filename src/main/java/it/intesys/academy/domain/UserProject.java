package it.intesys.academy.domain;

import jakarta.persistence.*;

@Entity
@Table(name="USERPROJECT")
public class UserProject {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projectid")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "personid")
    private Person person;
}
