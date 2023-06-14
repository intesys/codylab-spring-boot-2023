package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final static Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getProjects(@RequestParam String userName) {

        return ResponseEntity.ok(projectService.readProjectsWithIssues(userName));
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable int projectId, @RequestParam String username) {

        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId, username));
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO, @RequestParam String username) {
        if (projectDTO.getId() != null) {
            log.error("Bad request, id must be null when creating a new project");
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.ok(projectService.createProject(projectDTO, username));
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO, @RequestParam String username) {
        if (projectDTO.getId() == null) {
            log.error("Bad request, id must not be null when updating a project");
            return ResponseEntity.badRequest().build();
        }
        if (projectDTO.getId() != projectId) {
            log.error("Bad request, id in path and in body must be the same");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(projectService.updateProject(projectDTO, username));
    }

}
