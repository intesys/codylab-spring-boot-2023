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
public class CommentController {

    private final PropertyMessageService propertyMessageService;
    private final ProjectService projectService;

    public CommentController(PropertyMessageService propertyMessageService, ProjectService projectService) {
        this.propertyMessageService = propertyMessageService;
        this.projectService = projectService;
    }

    @GetMapping("/comments/{username}")
    public List<CommentDTO> getComments(@PathVariable String username, @RequestParam String issueId) {

        return projectService.readComments(Integer.parseInt(issueId),username);
    }

    @GetMapping("/comment/{username}")
    public CommentDTO getComment(@PathVariable String username, @RequestParam String issueId) {
        return projectService.readComment(Integer.parseInt(issueId),username);
    }
}
