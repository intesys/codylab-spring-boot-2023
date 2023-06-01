package it.intesys.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectDTO {
    private String name;
    private String description;

    private List<IssueDTO> issue = new ArrayList<>();

    private int id;

    public ProjectDTO() {
    }

    public ProjectDTO( int id,String name, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
    }


    public List<IssueDTO> getIssue() {
        return issue;
    }

    public void setIssue(List<IssueDTO> issue) {
        this.issue = issue;
    }

    public void addIssue(IssueDTO issue) {
        this.issue.add(issue);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
