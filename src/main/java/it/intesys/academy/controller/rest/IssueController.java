package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueController {
    Logger log= LoggerFactory.getLogger((ProjectController.class));
    private final IssueService issueService;

    public IssueController(IssueService issueService) {

        this.issueService = issueService;
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

}
