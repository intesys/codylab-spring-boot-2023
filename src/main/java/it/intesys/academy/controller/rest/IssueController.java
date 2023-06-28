package it.intesys.academy.controller.rest;

import it.intesys.academy.controller.rest.errors.BadRequestException;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueController {

    private final static Logger log = LoggerFactory.getLogger(IssueController.class);

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issues")
    public ResponseEntity<List<IssueDTO>> getIssues(
            @RequestParam Integer projectId, @RequestHeader(name = "X-User-Name") String userName) {

        return ResponseEntity.ok(issueService.readIssuesByProjectId(projectId, userName));
    }

    @GetMapping("/issues/{issueId}")
    public ResponseEntity<IssueDTO> getIssue(@PathVariable int issueId,
                                                 @RequestHeader(name = "X-User-Name") String username) {

        return ResponseEntity.ok(issueService.readIssueWithComments(issueId, username));
    }

    @PostMapping("/issues")
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO issueDTO,
                                                    @RequestHeader(name = "X-User-Name") String username) {
        if (issueDTO.getId() != null) {
            log.error("Bad request, id must be null when creating a new issue");
            throw new BadRequestException("ID");
        }

        if (!StringUtils.hasText(issueDTO.getDescription())
                || !StringUtils.hasText(issueDTO.getName())) {
            throw new RuntimeException("Invalid DTO");
        }

        return ResponseEntity.ok(issueService.createIssue(issueDTO, username));
    }

    @PutMapping("/issues/{issueId}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable int issueId, @RequestBody IssueDTO issueDTO,
                                                    @RequestHeader(name = "X-User-Name") String username) {
        if (issueDTO.getId() == null) {
            log.error("Bad request, id must not be null when updating a issue");
            return ResponseEntity.badRequest().build();
        }
        if (issueDTO.getId() != issueId) {
            log.error("Bad request, id in path and in body must be the same");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(issueService.updateIssue(issueDTO, username));
    }

    @DeleteMapping("/issues/{issueId}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Integer issueId,
                                              @RequestHeader("X-User-Name") String username) {
        issueService.deleteIssue(issueId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
