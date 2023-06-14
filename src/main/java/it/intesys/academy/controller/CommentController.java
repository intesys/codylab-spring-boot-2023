package it.intesys.academy.controller;

import it.intesys.academy.service.CommentService;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class CommentController {


    private final CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }

    @GetMapping("/comments/{issueId}")
    public String comments(Model model,@PathVariable Integer issueId, @RequestParam String userName) {

        model.addAttribute("username", userName);
        model.addAttribute("issueId", issueId);
        model.addAttribute("comments", commentService.getComments(issueId,userName));

        return "comment";

    }

}
