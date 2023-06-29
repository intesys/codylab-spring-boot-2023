package it.intesys.academy.controller.rest;

import it.intesys.academy.controller.openapi.model.IssueApiDTO;
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
/*
    private final static Logger log = LoggerFactory.getLogger(IssueController.class);

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issues")
    public ResponseEntity<List<IssueApiDTO>> getIssues(
            @RequestParam Integer projectId, @RequestHeader(name = "X-User-Name") String userName) {

        return ResponseEntity.ok(issueService.readIssuesByProjectId(projectId, userName));
    }

    @GetMapping("/issues/{issueId}")
    public ResponseEntity<IssueApiDTO> getIssue(@PathVariable int issueId,
                                                @RequestHeader(name = "X-User-Name") String username) {

        return ResponseEntity.ok(issueService.readIssueWithComments(issueId, username));
    }


    @PostMapping("/issues")
    public ResponseEntity<IssueApiDTO> createIssue(@RequestBody IssueApiDTO issueApiDTO,
                                                @RequestHeader(name = "X-User-Name") String username) {
        if (issueApiDTO.getId() != null) {
            throw new BadRequestException("Id must be null when creating a new issue");
        }

        if (!StringUtils.hasText(issueApiDTO.getDescription())
                || !StringUtils.hasText(issueApiDTO.getName())) {
            throw new BadRequestException("Invalid DTO");
        }

        return ResponseEntity.ok(issueService.createIssue(issueApiDTO, username));
    }

    @PutMapping("/issues/{issueId}")
    public ResponseEntity<IssueApiDTO> updateIssue(@PathVariable int issueId, @RequestBody IssueApiDTO issueApiDTO,
                                                @RequestHeader(name = "X-User-Name") String username) {
        if (issueApiDTO.getId() == null) {
            log.error("Bad request, id must not be null when updating a issue");
            return ResponseEntity.badRequest().build();
        }
        if (issueApiDTO.getId() != issueId) {
            log.error("Bad request, id in path and in body must be the same");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(issueService.updateIssue(issueApiDTO, username));
    }
    @DeleteMapping("/issues/{issueId}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Integer issueId,
                                            @RequestHeader("X-User-Name") String username) {
        issueService.deleteIssue(issueId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
*/
}
