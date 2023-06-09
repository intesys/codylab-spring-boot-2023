package it.intesys.academy.controller;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IssueController {

    private final PropertyMessageService propertyMessageService;
    private final ProjectService projectService;

    public IssueController(PropertyMessageService propertyMessageService, ProjectService projectService) {
        this.propertyMessageService = propertyMessageService;
        this.projectService = projectService;
    }

    @GetMapping("/issues/{username}")
    public List<IssueDTO> getIssues(@PathVariable String username, @RequestParam String projectId){
        return projectService.readIssues(Integer.parseInt(projectId),username);
    }

}