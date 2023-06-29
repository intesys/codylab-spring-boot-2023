package it.intesys.academy.controller.openapi;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.controller.rest.errors.BadRequestException;
import it.intesys.academy.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProjectApiDelegateImpl implements ProjectsApiDelegate{
    private final ProjectService projectService;

    public ProjectApiDelegateImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity<List<ProjectApiDTO>> getProjects(String xUserName) {
        List<ProjectApiDTO> projects = projectService.readProjectsWithIssues(xUserName);
        return ResponseEntity.ok(projects);
    }

    @Override
    public ResponseEntity<ProjectApiDTO> getProject(Integer projectId, String xUserName) {

        return ResponseEntity.ok(projectService.readProjectWithIssue(projectId, xUserName));
    }

    @Override
    public ResponseEntity<ProjectApiDTO> createProject(String xUserName, ProjectApiDTO projectApiDTO) {
        if (projectApiDTO.getId() != null) {
            throw new BadRequestException("Bad request, id must be null when creating a new project");

        }

        if (!StringUtils.hasText(projectApiDTO.getDescription())
                || !StringUtils.hasText(projectApiDTO.getName())) {
            throw new BadRequestException("Invalid DTO");
        }

        return ResponseEntity.ok(projectService.createProject(projectApiDTO, xUserName));
    }

    @Override
    public ResponseEntity<ProjectApiDTO> updateProject(Integer projectId, String xUserName, ProjectApiDTO projectApiDTO) {
        if (projectApiDTO.getId() == null) {
            throw new BadRequestException("Bad request, id must not be null when updating a project");

        }
        if (projectApiDTO.getId() != projectId) {
            throw new BadRequestException("Bad request, id in path and in body must be the same");

        }

        return ResponseEntity.ok(projectService.updateProject(projectApiDTO, xUserName));
    }

    @Override
    public ResponseEntity<Void> deleteProject(Integer projectId, String xUserName) {
        projectService.deleteProject(projectId, xUserName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
