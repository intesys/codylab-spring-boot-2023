package it.intesys.academy.mapper;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.entity.Issue;
import it.intesys.academy.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {

    public Issue toEntity(IssueDTO issueDTO) {
        Issue issue = new Issue();
        issue.setId(issueDTO.getId());
        issue.setDescription(issueDTO.getDescription());
        Project project = new Project();
        project.setId(issueDTO.getProjectId());
        issue.setProject(project);
        return issue;
    }

    public IssueDTO toDto(Issue issue) {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setDescription(issue.getDescription());
        issueDTO.setProjectId(issue.getProject().getId());
        return issueDTO;
    }
}
