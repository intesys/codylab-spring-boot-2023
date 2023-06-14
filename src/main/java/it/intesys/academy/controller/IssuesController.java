package it.intesys.academy.controller;

import it.intesys.academy.controller.rest.IssueController;
import it.intesys.academy.service.CommentService;
import it.intesys.academy.service.IssueService;
import it.intesys.academy.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class IssuesController {
    private final IssueService issueService;

    private final CommentService commentService;

    public IssuesController(IssueService issueService,CommentService commentService){
        this.issueService = issueService;
        this.commentService = commentService;
    }

    @GetMapping("/issues/{issueId}")
    public String issue(Model model, @PathVariable Integer issueId , @RequestParam String userName){
        model.addAttribute("username",userName);
        model.addAttribute("issue", issueService.getIssue(issueId,userName));
        model.addAttribute("comments", commentService.getComments(issueId,userName));
        return "issue";
    }

    /**@GetMapping("/issue/{projectId}/{issueId}")
    public String issue(Model model, @PathVariable Integer projectId, @PathVariable Integer issueId, @RequestParam String userName){
        model.addAttribute("username",userName);
        model.addAttribute("projectId",projectId);
        model.addAttribute("issues", issueService.readIssue(projectId,userName,issueId));
        return "issue";
    }*/
}
