package it.intesys.academy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_projects")
public class User_Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "projectid")
    private Integer projectId;

    public void setId(Integer id) {
        this.id = id;
    }


    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
