package it.intesys.academy.controller.rest;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ProjectApiDTO>> getProjects() {

        return ResponseEntity.ok(projectService.readProjectsWithIssues());
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectApiDTO> getProject(@PathVariable int projectId) {

        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId));
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectApiDTO> createProject(@RequestBody ProjectApiDTO projectDTO,
                                                    @RequestHeader(name = "X-User-Name") String username) {
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
    public ResponseEntity<ProjectApiDTO> updateProject(@PathVariable int projectId, @RequestBody ProjectApiDTO projectDTO,
                                                    @RequestHeader(name = "X-User-Name") String username) {
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
    public ResponseEntity<ProjectApiDTO> patchProject(@PathVariable int projectId, @RequestBody ProjectApiDTO projectDTO,
                                                   @RequestHeader("X-User-Name") String username) {
        if (projectDTO.getId() == null) {
            log.error("Bad request, id must not be null when updating a project");
            return ResponseEntity.badRequest().build();
        }
        if (projectDTO.getId() != projectId) {
            log.error("Bad request, id in path and in body must be the same");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(projectService.patchProject(projectDTO, username));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId,
                                              @RequestHeader("X-User-Name") String username) {
        projectService.deleteProject(projectId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
