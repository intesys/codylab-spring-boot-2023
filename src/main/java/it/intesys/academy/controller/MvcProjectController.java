package it.intesys.academy.controller;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mvc")
public class MvcProjectController {


    private final ProjectService projectService;

    public MvcProjectController(ProjectService projectService) {
        this.projectService = projectService;
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

    @GetMapping("/projects/new")
    public String createProjectPage(Model model, @RequestParam String userName) {
        model.addAttribute("username", userName);
        return "create-project";
    }

    @PostMapping("/projects")
    public String createNewProject(@ModelAttribute ProjectDTO projectDTO,@RequestParam String userName) {
        ProjectDTO project = projectService.createProject(projectDTO, userName);
                return "redirect:/mvc/projects/" + project.getId() + "?userName=" + userName;
    }

}
