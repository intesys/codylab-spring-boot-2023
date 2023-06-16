package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.UserProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueController {
    Logger log= LoggerFactory.getLogger((ProjectController.class));
    private final IssueService issueService;
    private final UserProjectService userProjectService;

    public IssueController(IssueService issueService, UserProjectService userProjectService) {

        this.issueService = issueService;
        this.userProjectService = userProjectService;
    }

    @GetMapping("/issues")
    public List<IssueDTO> getProject(@RequestParam int projectId) {

        return issueService.readIssues(projectId);
    }

    @PostMapping("/issues")
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO issueDTO, @RequestParam String username) {
        if (issueDTO.getId() != null) {
            log.error("Bad request, id must be null when creating a new issue");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(issueService.createIssue(issueDTO, username));
    }

    @PutMapping("/issues/{issueId}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable Integer issueId, @RequestBody IssueDTO issueDTO, @RequestParam String username){
        if(!userProjectService.canThisUserReadThisProject(username, issueDTO.getProjectId())) {throw new RuntimeException("Security constraints violation");}
        if (issueDTO.getId() == null) {
            log.error("Bad request,id can't be null when updating a issue");
            ResponseEntity.badRequest().build();
        }
        if (issueDTO.getId() != issueId) {
            log.error("Bad request,id in path and in body must be equal when updating a issue");
            ResponseEntity.badRequest().build();
        }
        issueService.updateIssue(issueDTO);
        return ResponseEntity.ok(issueService.readIssue(issueId));
    }


    @DeleteMapping("/issues/{issueId}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Integer issueId, @RequestParam String username) {
        if (!userProjectService.canThisUserReadThisProject(username, issueService.readIssueWithComments(issueId).getProjectId()))
            throw new RuntimeException("Security constraints violation");
        issueService.deleteIssue(issueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
