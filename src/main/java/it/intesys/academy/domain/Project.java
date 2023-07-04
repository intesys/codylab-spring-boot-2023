package it.intesys.academy.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="Project")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "project")
    private List<Issue> issues;

    @ManyToOne
    @JoinColumn(name = "authorid")
    private Person person;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private ProjectSettings details;

    @ManyToMany(mappedBy = "userProjects")
    Set<Person> enabledUsers;

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

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public Person getUser() {

        return person;
    }

    public void setUser(Person person) {

        this.person = person;
    }

    public ProjectSettings getDetails() {
        return details;
    }

    public void setDetails(ProjectSettings details) {
        this.details = details;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Person> getEnabledUsers() {
        return enabledUsers;
    }

    public void setEnabledUsers(Set<Person> enabledUsers) {
        this.enabledUsers = enabledUsers;
    }
}
