package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {

        this.issueService = issueService;
    }

    @GetMapping("projects/{projectId}/issues")
    public List<IssueDTO> getProjectIssues(@PathVariable int projectId, @RequestParam String userName) {

        return issueService.readIssues(projectId,userName);
    }

    @PostMapping("/issues")
    public ResponseEntity<IssueDTO> postIssues(@RequestBody IssueDTO issueDTO,
                                              @RequestParam String username){
        return ResponseEntity.ok(issueService.createIssue(issueDTO, username));
    }

    @PutMapping("/issues/{issueId}")
    public ResponseEntity<IssueDTO> putIssues(@RequestBody IssueDTO issueDTO,
                              @PathVariable Integer issueId,
                              @RequestParam String username
                              ){
        return ResponseEntity.ok(issueService.updateIssue(issueDTO, username, issueId));
    }

    @DeleteMapping("/issues/{issueId}")
    public ResponseEntity<Void> deleteIssues(
                            @PathVariable Integer issueId,
                            @RequestParam String username){
        issueService.deleteIssue(username,issueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
