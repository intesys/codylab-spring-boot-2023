package it.intesys.academy.controller.openapi;

import it.intesys.academy.controller.openapi.model.IssueApiDTO;
import it.intesys.academy.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueApiDelegateImpl implements IssuesApiDelegate{

    private final IssueService issueService;

    public IssueApiDelegateImpl(IssueService issueService) {
        this.issueService = issueService;
    }

    @Override
    public ResponseEntity<IssueApiDTO> createIssue(IssueApiDTO issueApiDTO) {
        return ResponseEntity.ok(issueService.createIssue(issueApiDTO));
    }

    @Override
    public ResponseEntity<IssueApiDTO> getIssue(Integer issueId) {
        return ResponseEntity.ok(issueService.readIssueWithComments(issueId));
    }

    @Override
    public ResponseEntity<List<IssueApiDTO>> getIssues(Integer projectId) {
        return ResponseEntity.ok(issueService.readIssuesByProjectId(projectId));
    }

    @Override
    public ResponseEntity<Void> issuesIssueIdDelete(Integer issueId) {
        issueService.deleteIssue(issueId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IssueApiDTO> issuesIssueIdPut(Integer issueId, IssueApiDTO issueApiDTO) {
        return ResponseEntity.ok(issueService.updateIssue(issueApiDTO));
    }
}
