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

    @GetMapping("/issues/{projectId}")
    public List<IssueDTO> getProject(@PathVariable int projectId, @RequestParam String userName) {

        return issueService.readIssues(projectId,userName);
    }

}
