package it.intesys.academy.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "Userproject")
public class UserProject {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="personid")
    private Person person;

    @ManyToOne
    @JoinColumn(name="projectid")
    private  Project project;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
