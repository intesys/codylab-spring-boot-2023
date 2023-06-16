package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.IssueService;
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

    @PostMapping("/projects/{projectId}/issues")
    public IssueDTO postIssues(@RequestBody IssueDTO issueDTO,
                               @PathVariable Integer projectId,
                               @RequestParam String username){
        return issueService.createIssue(issueDTO, projectId,username);
    }

    @PutMapping("/projects/{projectId}/issues/{issueId}")
    public IssueDTO putIssues(@RequestBody IssueDTO issueDTO,
                              @PathVariable Integer projectId,
                              @PathVariable Integer issueId,
                              @RequestParam String username
                              ){
        return issueService.updateIssue(issueDTO,projectId, username, issueId);
    }

}
