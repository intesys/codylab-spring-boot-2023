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
        model.addAttribute("projects", projectService.readProjectsWithIssues());

        return "index";

    }

    @GetMapping("/projects/{projectId}")
    public String getProjectDetails(Model model,
                                    @PathVariable("projectId") Integer projectId,
                                    @RequestParam String userName) {

        model.addAttribute("username", userName);
        model.addAttribute("project", projectService.readProjectWithIssue(projectId));

        return "project";

    }

    @GetMapping("/projects/new")
    public String createNewProject(Model model, @RequestParam String userName){
        model.addAttribute("username",userName);
        return  "create-project";
    }

    @PostMapping("/projects")
    public String postNewProject(@ModelAttribute ProjectApiDTO projectApiDTO){
        ProjectApiDTO newprojectDTO = projectService.createProject(projectApiDTO);
        return "redirect:/mvc/projects/"+ newprojectDTO.getId();
    }

}
