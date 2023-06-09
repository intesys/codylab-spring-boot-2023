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

    @GetMapping("/comments/{issueId}")
    public List<CommentDTO> getComments(@RequestParam String username, @PathVariable Integer issueId) {
        return projectService.readComments(issueId,username);
    }

    @GetMapping("/comment/{issueId}")
    public CommentDTO getComment(@RequestParam String username, @PathVariable Integer issueId) {
        return projectService.readComment(issueId,username);
    }
}
