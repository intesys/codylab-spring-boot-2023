package it.intesys.academy.mapper;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.entity.Issue;
import it.intesys.academy.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {
    public IssueDTO toDto(Issue issue){
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setDescription(issue.getDescription());
        issueDTO.setName(issue.getName());
        issueDTO.setProjectId(issue.getProject().getId());
        issueDTO.setAuthor(issue.getAuthor());
        return issueDTO;
    }

    public Issue toEntity(IssueDTO issueDTO){
        Issue issue = new Issue();
        issue.setId(issueDTO.getId());
        issue.setDescription(issueDTO.getDescription());
        issue.setName(issueDTO.getName());
        Project project = new Project();
        project.setId(issueDTO.getId());
        issue.setProject(project);
        issue.setAuthor(issueDTO.getAuthor());
        return issue;
    }

}
