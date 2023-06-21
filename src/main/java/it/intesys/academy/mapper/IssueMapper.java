package it.intesys.academy.mapper;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.entity.Issues;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {

    public Issues toEntity(IssueDTO issueDTO){
        Issues issues = new Issues();
        issues.setId(issueDTO.getId());
        issues.setName(issueDTO.getName());
        issues.setDescription(issueDTO.getDescription());
        issues.setAuthor(issueDTO.getAuthor());
        issues.setProjectid(issueDTO.getProjectId());
        return issues;
    }

    public IssueDTO toDTO(Issues issue){
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(issue.getId());
        issueDTO.setName(issue.getName());
        issueDTO.setDescription(issue.getDescription());
        issueDTO.setAuthor(issue.getAuthor());
        issueDTO.setProjectId(issue.getProjectid());
        return issueDTO;
    }
}
