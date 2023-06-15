package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<ProjectDTO> getProjects(@RequestParam String userName) {

        return projectService.readProjectsWithIssues(userName);
    }

    @GetMapping("/projects/{projectId}")
    public ProjectDTO getProject(@PathVariable int projectId, @RequestParam String username) {

        return projectService.readProjectWithIssue(projectId, username);
    }
    @PostMapping("/projects")
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO, @RequestParam String username) {
        if (projectDTO.getId() != null) {
            throw new RuntimeException("Bad request, id must be null when creating a new project");
        }
        return projectService.createProject(projectDTO, username);
    }

    @PutMapping("/projects/{projectId}")
    public ProjectDTO updateProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO, @RequestParam String username) {
        if (projectDTO.getId() == null) {
            throw new RuntimeException("Bad request, id must not be null when updating a project");
        }
        if (projectDTO.getId() != projectId) {
            throw new RuntimeException("Bad request, id in path and in body must be the same");
        }
        return projectService.updateProject(projectDTO, username);
    }

}
