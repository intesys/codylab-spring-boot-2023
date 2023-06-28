package it.intesys.academy.controller.openapi;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectApiDelegateImpl implements ProjectsApiDelegate{
    private final ProjectService projectService;

    public ProjectApiDelegateImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity<List<ProjectApiDTO>> getProjects() {
        List<ProjectApiDTO> projectDTOS = projectService.readProjectsWithIssues();
        return ResponseEntity.ok(projectDTOS);
    }
}
