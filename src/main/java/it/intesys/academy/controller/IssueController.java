package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.IssueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {

        this.issueService = issueService;
    }

    @GetMapping("/issues")
    public List<IssueDTO> getProject(@RequestParam int projectId) {

        return issueService.readIssues(projectId);
    }

}
