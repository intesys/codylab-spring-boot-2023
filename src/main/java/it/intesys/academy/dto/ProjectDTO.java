package it.intesys.academy.dto;

import it.intesys.academy.domain.ProjectSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectDTO {

    private Integer id;

    private String name;

    private String description;

    private ArrayList<IssueDTO> issues = new ArrayList<>();
    private ProjectSettings projectSettings;

    public ProjectSettings getProjectSettings() {
        return projectSettings;
    }

    public void setProjectSettings(ProjectSettings projectSettings) {
        this.projectSettings = projectSettings;
    }

    public void addIssue(IssueDTO issue) {
        issues.add(issue);
    }

    public List<IssueDTO> getIssues() {
        return issues;
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

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public ProjectDTO() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDTO that = (ProjectDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean hasSameFields(ProjectDTO projectDTO) {
        return this.name.equals(projectDTO.getName()) && this.description.equals(projectDTO.getDescription());
    }

}
