package it.intesys.academy.controller;

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
public class IndexController {


    private final ProjectService projectService;
    private final IssueService issueService;

    public IndexController(ProjectService projectService, IssueService issueService) {

        this.projectService = projectService;
        this.issueService = issueService;
    }

    @GetMapping("/list-projects")
    public String index(Model model, @RequestParam String userName) {

        model.addAttribute("username", userName);
        model.addAttribute("projects", projectService.readProjectsWithIssues(userName));

        return "index";

    }

    @GetMapping("/project/{projectId}")
    public String index(Model model, @PathVariable Integer projectId, @RequestParam String userName){
        model.addAttribute("username",userName);
        model.addAttribute("project", projectService.readProjectWithIssue(projectId,userName));
        model.addAttribute("issues", issueService.readIssues(projectId,userName));
        return "project";
    }

}
