package it.intesys.academy.dto;

public class UserProjectDTO {

    private Integer id;     //use of wrapper instead of primitive type to avoid problems if null
    private Integer projectId;
    private String username;

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public Integer getProjectId() {

        return projectId;
    }

    public void setProjectId(Integer projectId) {

        this.projectId = projectId;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

}
