package it.intesys.academy.controller;


import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    private final PropertyMessageService propertyMessageService;
    private final ProjectService projectService;

    public ProjectController(PropertyMessageService propertyMessageService, ProjectService projectService) {
        this.propertyMessageService = propertyMessageService;
        this.projectService = projectService;
    }

    @GetMapping("/project/{projectId}")
    public ProjectDTO getProjects(@PathVariable String projectId) {
        return projectService.readProject(Integer.parseInt(projectId));
    }
}
