package it.intesys.academy.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "project_settings")
public class ProjectSettings {

    @Id
    @Column(name="project_id")
    private Integer projectId;

    @Column(name="show_released_issues")
    private Boolean showReleasedIssues;

    @OneToOne
    @MapsId
    @JoinColumn(name = "project_id")
    private Project project;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer id) {
        this.projectId = id;
    }

    public Boolean getShowReleasedIssues() {
        return showReleasedIssues;
    }

    public void setShowReleasedIssues(Boolean showReleasedIssues) {
        this.showReleasedIssues = showReleasedIssues;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
