package it.intesys.academy.controller;

import it.intesys.academy.controller.rest.IssueController;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class IssuesController {
    private final IssueService issueService;

    private final ProjectService projectService;

    public IssuesController(IssueService issueService,ProjectService projectService){
        this.issueService = issueService;
        this.projectService = projectService;
    }

    @GetMapping("/issues/{projectId}")
    public String issue(Model model, @PathVariable Integer projectId , @RequestParam String userName){
        model.addAttribute("username",userName);
        model.addAttribute("projectId",projectId);
        model.addAttribute("projects", projectService.readProjectWithIssue(projectId,userName));
        model.addAttribute("issues", issueService.readIssues(projectId,userName));
        return "issue";
    }

    @GetMapping("/issue/{projectId}/{issueId}")
    public String issue(Model model, @PathVariable Integer projectId, @PathVariable Integer issueId, @RequestParam String userName){
        model.addAttribute("username",userName);
        model.addAttribute("projectId",projectId);
        model.addAttribute("issues", issueService.readIssue(projectId,userName,issueId));
        return "issue";
    }
}
