package it.intesys.academy.dto;

import java.util.ArrayList;
import java.util.List;
public class ProjectDTO {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIssues(IssueDTO issue) {
        issues.add(issue);
    }
    public List<IssueDTO> getIssues(){
        return issues;
    }

    public ProjectDTO(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    private int id;
    private String name;
    private String description;
    private List<IssueDTO> issues = new ArrayList<>();

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
}
