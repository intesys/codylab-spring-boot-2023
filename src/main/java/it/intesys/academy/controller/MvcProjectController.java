package it.intesys.academy.controller;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.dto.ProjectDTO;
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
    public String getNewProjectForm(Model model, @RequestParam String userName) {

        model.addAttribute("username", userName);

        return "create-project";

    }

    @PostMapping("/projects")
    public String createProject(
            @RequestParam String userName,//not a good practice, just as an example
            @ModelAttribute("project") ProjectApiDTO projectDTO) {

        ProjectApiDTO newProject = projectService.createProject(projectDTO, userName);
        return "redirect:/mvc/projects/" + newProject.getId() + "?userName=" + userName;
    }

}
