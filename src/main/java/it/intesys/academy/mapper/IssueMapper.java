package it.intesys.academy.mapper;

import it.intesys.academy.domain.Issue;
import it.intesys.academy.dto.IssueDTO;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {

    public Issue toEntity(IssueDTO issueDTO) {
        Issue issue = new Issue();
        issue.setId(issueDTO.getId());
        issue.setName(issueDTO.getName());
        issue.setDescription(issueDTO.getDescription());
        issue.setAuthor(issueDTO.getAuthor());
        issue.setProjectId(issueDTO.getProjectId());
        return issue;
    }

    public IssueDTO toDto(Issue issue) {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setName(issue.getName());
        issueDTO.setDescription(issue.getDescription());
        issueDTO.setAuthor(issue.getAuthor());
        issueDTO.setProjectId(issue.getProjectId());
        return issueDTO;
    }
}
