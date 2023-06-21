package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Comments;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private static final Logger log = LoggerFactory.getLogger(IssueService.class);
    private final SettingsService settingsService;

    private final UserProjectService userProjectService;

    private  final IssueService issueService;

    private final CommentMapper commentMapper;

    private final ProjectService projectService;
    public CommentService(ProjectService projectService,CommentRepository commentRepository, SettingsService settingsService, IssueService issueService, UserProjectService userProjectService, CommentMapper commentMapper){
        this.commentRepository = commentRepository;
        this.settingsService = settingsService;
        this.userProjectService = userProjectService;
        this.issueService = issueService;
        this.projectService = projectService;
        this.commentMapper = commentMapper;
    }

    public List<CommentDTO> getComments(Integer issueId,String userName){
        IssueDTO issue = issueService.getIssue(issueId,userName);
        //log.info("Ecco "+issue.getNome());
        List<Comments> comments = commentRepository.getComments(issueId);
        return comments.stream().map(commentMapper::toDTO).toList();
    }

    public CommentDTO getComment(String userName, Integer commentId){
        Comments comments = commentRepository.readComment(commentId);
        IssueDTO issue = issueService.getIssue(comments.getIssueId(),userName);
        return commentMapper.toDTO(comments);
    }

    public CommentDTO createComment(CommentDTO commentDTO, String username){
        Comments comment = commentMapper.toEntity(commentDTO);
        IssueDTO issue = issueService.getIssue(comment.getIssueId(),username);
        return commentMapper.toDTO(commentRepository.insertComment(comment));
    }

    public CommentDTO updateComment(CommentDTO commentDTO, String username){
        Comments comment = commentMapper.toEntity(commentDTO);
        IssueDTO issue = issueService.getIssue(comment.getIssueId(),username);
        return commentMapper.toDTO(commentRepository.updateComment(comment));
    }

    public void deleteComment(Integer commentId, String username){
        Comments comment = commentRepository.readComment(commentId);
        IssueDTO issue = issueService.getIssue(comment.getIssueId(),username);
        commentRepository.removeComment(commentId);
    }

}
