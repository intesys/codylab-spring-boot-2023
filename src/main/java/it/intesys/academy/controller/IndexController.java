package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
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

    @GetMapping("/projects")
    public String getProjectList(Model model, @RequestParam String userName) {

        model.addAttribute("username", userName);
        model.addAttribute("projects", projectService.readProjectsWithIssues(userName));

        return "index";

    }

    @GetMapping("/projects/{projectId}")
    public String getProjectDetails(Model model,
                                    @PathVariable("projectId") Integer projectId,
                                    @RequestParam String userName) {

        model.addAttribute("username", userName);
        model.addAttribute("project", projectService.readProjectWithIssue(projectId, userName));

        return "project";

    }

    @GetMapping("/issues/{issueId}")
    public String getIssueDetails(Model model,
                                    @PathVariable("issueId") Integer issueId,
                                    @RequestParam String userName) {

        IssueDTO issueDTO = issueService.readIssueWithComments(issueId);
        model.addAttribute("issue", issueDTO);
        model.addAttribute("username", userName);
        model.addAttribute("project", projectService.readProject(issueDTO.getProjectId(), userName));

        return "issue";

    }

}
