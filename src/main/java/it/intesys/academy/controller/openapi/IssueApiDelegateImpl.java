package it.intesys.academy.controller.openapi;

import it.intesys.academy.controller.openapi.model.IssueApiDTO;
import it.intesys.academy.controller.rest.errors.BadRequestException;
import it.intesys.academy.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class IssueApiDelegateImpl implements IssuesApiDelegate{
    private final IssueService issueService;

    public IssueApiDelegateImpl(IssueService issueService) {
        this.issueService = issueService;
    }

    @Override
    public ResponseEntity<IssueApiDTO> getIssue(Integer issueId, String xUserName) {
        return ResponseEntity.ok(issueService.readIssueWithComments(issueId, xUserName));
    }

    @Override
    public ResponseEntity<List<IssueApiDTO>> getIssues(String xUserName, Integer projectId) {
        return ResponseEntity.ok(issueService.readIssuesByProjectId(projectId, xUserName));

    }

    @Override
    public ResponseEntity<IssueApiDTO> createIssue(String xUserName, IssueApiDTO issueApiDTO) {
        if (issueApiDTO.getId() != null) {
            throw new BadRequestException("Id must be null when creating a new issue");
        }

        if (!StringUtils.hasText(issueApiDTO.getDescription())
                || !StringUtils.hasText(issueApiDTO.getName())) {
            throw new BadRequestException("Invalid DTO");
        }

        return ResponseEntity.ok(issueService.createIssue(issueApiDTO, xUserName));
    }

    @Override
    public ResponseEntity<IssueApiDTO> updateIssue(Integer issueId, String xUserName, IssueApiDTO issueApiDTO) {

        if (issueApiDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (issueApiDTO.getId() != issueId) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(issueService.updateIssue(issueApiDTO, xUserName));
    }

    @Override
    public ResponseEntity<Void> deleteIssue(Integer issueId, String xUserName) {
        issueService.deleteIssue(issueId, xUserName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
