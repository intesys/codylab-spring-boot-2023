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
    public ResponseEntity<List<ProjectApiDTO>> getProjects(String xUserName) {
        List<ProjectApiDTO> projectDTOS = projectService.readProjectsWithIssues(xUserName);
        return ResponseEntity.ok(projectDTOS);
    }

    @Override
    public ResponseEntity<ProjectApiDTO> createProject(String xUserName, ProjectApiDTO projectApiDTO) {
        ProjectApiDTO project = projectService.createProject(projectApiDTO, xUserName);
        return ResponseEntity.ok(project);
    }
}
