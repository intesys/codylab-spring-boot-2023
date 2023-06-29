package it.intesys.academy.mapper;

import it.intesys.academy.controller.openapi.model.IssueApiDTO;
import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.IssueDTO;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {

    public Issue toEntity(IssueApiDTO issueApiDTO) {
        Issue issue = new Issue();
        issue.setId(issueApiDTO.getId());
        issue.setName(issueApiDTO.getName());
        issue.setDescription(issueApiDTO.getDescription());
        issue.setAuthor(issueApiDTO.getAuthor());
        Project project = new Project();
        project.setId(issueApiDTO.getProjectId());
        issue.setProject(project);
        return issue;
    }

    public IssueDTO toDto(Issue issue) {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setName(issue.getName());
        issueDTO.setDescription(issue.getDescription());
        issueDTO.setAuthor(issue.getAuthor());
        issueDTO.setProjectId(issue.getProject().getId());
        return issueDTO;
    }
    public IssueApiDTO toApiDto(Issue issue) {
        IssueApiDTO issueApiDTO = new IssueApiDTO();
        issueApiDTO.setId(issue.getId());
        issueApiDTO.setName(issue.getName());
        issueApiDTO.setDescription(issue.getDescription());
        issueApiDTO.setAuthor(issue.getAuthor());
        issueApiDTO.setProjectId(issue.getProject().getId());
        return issueApiDTO;
    }
}
