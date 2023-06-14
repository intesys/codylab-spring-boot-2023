package it.intesys.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectDTO {

    private Integer id;

    private String name;

    private String description;

    private ArrayList<IssueDTO> issues = new ArrayList<>();

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

}
