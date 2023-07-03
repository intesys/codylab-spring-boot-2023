package it.intesys.academy.controller;

import it.intesys.academy.controller.openapi.model.IssueApiDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class MvcIssueController {


    private final ProjectService projectService;

    private final IssueService issueService;


    public MvcIssueController(ProjectService projectService, IssueService issueService) {

        this.projectService = projectService;
        this.issueService = issueService;
    }

    @GetMapping("/issues/{issueId}")
    public String getIssueDetails(Model model,
                                  @PathVariable("issueId") Integer issueId) {

        IssueApiDTO issueDTO = issueService.readIssueWithComments(issueId);
        var userName = SecurityUtils.getUserName();
        model.addAttribute("issue", issueDTO);
        model.addAttribute("username", userName);
        model.addAttribute("project", projectService.readProject(issueDTO.getProjectId()));

        return "issue";

    }

}
