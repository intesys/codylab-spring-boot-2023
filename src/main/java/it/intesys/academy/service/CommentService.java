package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private static final Logger log = LoggerFactory.getLogger(IssueService.class);
    private final SettingsService settingsService;

    private final UserProjectService userProjectService;

    private  final IssueService issueService;

    private final ProjectService projectService;
    public CommentService(ProjectService projectService,CommentRepository commentRepository, SettingsService settingsService, IssueService issueService, UserProjectService userProjectService){
        this.commentRepository = commentRepository;
        this.settingsService = settingsService;
        this.userProjectService = userProjectService;
        this.issueService = issueService;
        this.projectService = projectService;
    }

    public List<CommentDTO> getComments(Integer issueId,String userName){
        IssueDTO issue = issueService.getIssue(issueId,userName);
        log.info("Ecco "+issue.getNome());
        return commentRepository.getComments(issueId);
    }


}
