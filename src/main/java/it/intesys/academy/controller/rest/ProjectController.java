package it.intesys.academy.controller.rest;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.controller.rest.errors.BadRequestException;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProjectController {
/*
    private final static Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectApiDTO> getProject(@PathVariable int projectId,
                                                        @RequestHeader(name = "X-User-Name") String username) {

        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId, username));
    }


    @PostMapping("/projects")
    public ResponseEntity<ProjectApiDTO> createProject(@RequestBody ProjectApiDTO projectApiDTO,
                                                    @RequestHeader(name = "X-User-Name") String username) {
        if (projectApiDTO.getId() != null) {
            throw new BadRequestException("Bad request, id must be null when creating a new project");

        }

        if (!StringUtils.hasText(projectApiDTO.getDescription())
                || !StringUtils.hasText(projectApiDTO.getName())) {
            throw new BadRequestException("Invalid DTO");
        }

        return ResponseEntity.ok(projectService.createProject(projectApiDTO, username));
    }

    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO,
                                                    @RequestHeader(name = "X-User-Name") String username) {
        if (projectDTO.getId() == null) {
            throw new BadRequestException("Bad request, id must not be null when updating a project");

        }
        if (projectDTO.getId() != projectId) {
            throw new BadRequestException("Bad request, id in path and in body must be the same");

        }

        return ResponseEntity.ok(projectService.updateProject(projectDTO, username));
    }

    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> patchProject(@PathVariable int projectId, @RequestBody ProjectDTO projectDTO,
                                                   @RequestHeader("X-User-Name") String username) {
        if (projectDTO.getId() == null) {
            throw new BadRequestException("Bad request, id must not be null when updating a project");

        }
        if (projectDTO.getId() != projectId) {
            throw new BadRequestException("Bad request, id in path and in body must be the same");

        }

        return ResponseEntity.ok(projectService.patchProject(projectDTO, username));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer projectId,
                                              @RequestHeader("X-User-Name") String username) {
        projectService.deleteProject(projectId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/


}
