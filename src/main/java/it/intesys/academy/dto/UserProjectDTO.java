package it.intesys.academy.dto;

public class UserProjectDTO {

    private Integer id;
    private Integer projectId;
    private String username;

    public Integer getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Integer getProjectId() {

        return projectId;
    }

    public void setProjectId(int projectId) {

        this.projectId = projectId;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

}
