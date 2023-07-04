package it.intesys.academy.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "userproject")
public class User_Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "authorid")
    private Integer authorid;



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

}
