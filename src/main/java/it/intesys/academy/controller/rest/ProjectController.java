package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<List<ProjectDTO>> getProjects(@RequestHeader(name = "username") String userName) {

        return ResponseEntity.ok(projectService.readProjectsWithIssues(userName));
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable int projectId, @RequestHeader(name = "username") String username) {

        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId, username));
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO, @RequestHeader(name = "username") String username) {
        if (projectDTO.getId() != null) {
            log.error("Bad request, id must be null when creating a new project");
            return ResponseEntity.badRequest().build();
        }

        if (!StringUtils.hasText(projectDTO.getDescription())
                || !StringUtils.hasText(projectDTO.getName())) {
            throw new RuntimeException("Invalid DTO");
        }

        return ResponseEntity.ok(projectService.createProject(projectDTO, username));
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO, @RequestHeader(name = "username") String username) {
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

    @PatchMapping("/projects/{projectId}")
    public ProjectDTO patchProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO, @RequestParam String username) {
        if (projectDTO.getId() == null) {
            throw new RuntimeException("Bad request, id must not be null when updating a project");
        }
        if (projectDTO.getId() != projectId) {
            throw new RuntimeException("Bad request, id in path and in body must be the same");
        }
        return projectService.patchProject(projectDTO, username);
    }

    @DeleteMapping("/projects/{projectId}")
    public void deleteProject(@PathVariable Integer projectId, @RequestParam String username) {
        projectService.deleteProject(projectId, username);
    }


}
