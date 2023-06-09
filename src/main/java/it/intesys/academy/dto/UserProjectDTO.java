package it.intesys.academy.dto;

public class UserProjectDTO {
    private int id;
    private String username;
    private int projectId;

    public UserProjectDTO() {
    }

    public UserProjectDTO(int id, String username, int projectId) {
        this.id = id;
        this.username = username;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }
}
