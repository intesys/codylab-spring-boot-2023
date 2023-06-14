package it.intesys.academy.controller;

import it.intesys.academy.controller.rest.IssueController;
import it.intesys.academy.service.IssueService;
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

    public IssuesController(IssueService issueService){
        this.issueService = issueService;
    }

    @GetMapping("/issues/{projectId}")
    public String issue(Model model, @PathVariable Integer projectId , @RequestParam String userName){
        model.addAttribute("username",userName);
        model.addAttribute("issues", issueService.readIssues(projectId,userName));
        return "issue";
    }
}
