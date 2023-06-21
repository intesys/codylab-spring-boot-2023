package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list-projects")
    public List<ProjectDTO> getProjects(@RequestParam String userName) {

        return projectService.readProjectsWithIssues(userName);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable int projectId, @RequestParam String username) {

        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId, username));
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> postProject(@RequestBody ProjectDTO projectDTO
                                 ,@RequestParam String username){
        return ResponseEntity.ok(projectService.createProject(projectDTO,username));
    }

    @PutMapping("/projects/")
    public ResponseEntity<ProjectDTO> putProject(@RequestBody ProjectDTO projectDTO,
                                 @RequestParam String username){
        return ResponseEntity.ok(projectService.updateProject(projectDTO,username));
    }

    @PatchMapping("/projects/")
    public ResponseEntity<ProjectDTO> patchProject(@RequestBody ProjectDTO projectDTO,
                                                   @RequestParam String username){
        return ResponseEntity.ok(projectService.patchProject(projectDTO, username));
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteproject(@PathVariable Integer projectId, @RequestParam String username){
        projectService.deleteProject(projectId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
