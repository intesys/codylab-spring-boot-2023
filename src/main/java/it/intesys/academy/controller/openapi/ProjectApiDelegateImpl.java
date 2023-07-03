package it.intesys.academy.controller.openapi;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectApiDelegateImpl implements ProjectsApiDelegate{

    private final ProjectService projectService;

    public ProjectApiDelegateImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity<ProjectApiDTO> getProject(Integer projectId) {
        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId));
    }

    @Override
    public ResponseEntity<List<ProjectApiDTO>> getProjects() {
        return ResponseEntity.ok(projectService.readProjectsWithIssues());
    }

    @Override
    public ResponseEntity<ProjectApiDTO> createProject(ProjectApiDTO projectApiDTO) {
        return ResponseEntity.ok(projectService.createProject(projectApiDTO));
    }

    @Override
    public ResponseEntity<ProjectApiDTO> updateProject(Integer projectId, ProjectApiDTO projectApiDTO) {
        return ResponseEntity.ok(projectService.updateProject(projectApiDTO));
    }

    @Override
    public ResponseEntity<Void> deleteProject(Integer projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectApiDTO> patchProject(Integer projectId, ProjectApiDTO projectApiDTO) {
        return ResponseEntity.ok(projectService.patchProject(projectApiDTO));
    }

}
