package it.intesys.academy.controller.rest;

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

    private Logger log = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<ProjectDTO> getProjects(@RequestParam String userName) {

        return projectService.readProjectsWithIssues(userName);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable int projectId, @RequestHeader("X-User-Name") String username) {

        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId, username));
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO,
                                    @RequestHeader("X-User-Name") String username) {

        if (!StringUtils.hasText(projectDTO.getDescription())
        || !StringUtils.hasText(projectDTO.getName())) {
            return ResponseEntity.badRequest().build();
        }

        ProjectDTO projectDto = projectService.createProject(projectDTO, username);
        return new ResponseEntity<>(projectDto, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO, @RequestHeader("X-User-Name")  String username) {
        if (projectDTO.getId() == null) {
            log.error("Bad request, id must not be null when updating a project");
            return ResponseEntity.badRequest().build();
        }
        if (projectDTO.getId() != projectId) {
            log.error("Bad request, id in path and in body must be the same");
            return ResponseEntity.badRequest().build();
        }
        ProjectDTO updatedProject = projectService.updateProject(projectDTO, username);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> patchProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO, @RequestHeader("X-User-Name")  String username) {
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
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId, @RequestHeader("X-User-Name") String username) {
        projectService.deleteProject(projectId, username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
