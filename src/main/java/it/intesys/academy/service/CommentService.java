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
        //log.info("Ecco "+issue.getNome());
        return commentRepository.getComments(issueId);
    }

    public CommentDTO getComment(Integer issueId,String userName, Integer commentId){
        IssueDTO issue = issueService.getIssue(issueId,userName);
        return commentRepository.readComment(commentId);
    }

    public CommentDTO createComment(CommentDTO commentDTO, String username){
        IssueDTO issue = issueService.getIssue(commentDTO.getIssueId(),username);
        int id = commentRepository.insertComment(commentDTO);
        return commentRepository.readComment(id);
    }

    public CommentDTO updateComment(CommentDTO commentDTO, String username){
        IssueDTO issue = issueService.getIssue(commentDTO.getIssueId(),username);
        commentRepository.updateComment(commentDTO);
        return commentRepository.readComment(issue.getId());
    }

    public void deleteComment(Integer commentId, String username){
        CommentDTO commentDTO = commentRepository.readComment(commentId);
        IssueDTO issue = issueService.getIssue(commentDTO.getIssueId(),username);
        commentRepository.removeComment(commentId);
    }

}
