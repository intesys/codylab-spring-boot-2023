package it.intesys.academy.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Person")
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @OneToMany(mappedBy = "person")
    private List<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "person_project",
            joinColumns = { @JoinColumn(name = "person_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id", referencedColumnName = "id") }
    )
    private Set<Project> userProjects = new HashSet<>();

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getSurname() {

        return surname;
    }

    public void setSurname(String surname) {

        this.surname = surname;
    }

    public List<Project> getProjects() {

        return projects;
    }

    public void setProjects(List<Project> projects) {

        this.projects = projects;
    }

}
