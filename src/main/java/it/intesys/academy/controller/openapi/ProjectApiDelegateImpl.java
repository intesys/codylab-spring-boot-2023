package it.intesys.academy.controller.openapi;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.service.ProjectService;
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
    public ResponseEntity<ProjectApiDTO> getProject(Integer projectId, String xUserName) {
        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId,xUserName));
    }

    @Override
    public ResponseEntity<List<ProjectApiDTO>> getProjects(String xUserName) {
        return ResponseEntity.ok(projectService.readProjectsWithIssues(xUserName));
    }



    @Override
    public ResponseEntity<ProjectApiDTO> createProject(String xUserName, ProjectApiDTO projectApiDTO) {
        return ResponseEntity.ok(projectService.createProject(projectApiDTO,xUserName));
    }
}
