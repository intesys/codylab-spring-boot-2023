package it.intesys.academy.service;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.controller.rest.CommentController;
import it.intesys.academy.domain.Comment;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final UserProjectService userProjectService;
    private final CommentRepository commentRepository;

    private final IssueRepository issueRepository;

    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper,CommentRepository commentRepository, UserProjectService userProjectService, IssueRepository issueRepository){
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
        this.issueRepository = issueRepository;
        this.commentMapper = commentMapper;
    }

    public List<CommentApiDTO> readCommentsByIssueId(Integer issueId, String userName) {

        log.info("Reading comments for issue {}", issueId);
        Integer projectId = issueRepository.findById(issueId).get()
                .getProject().getId();

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }

        return commentRepository.findCommentsByIssueId(issueId).stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    public CommentApiDTO readComments(Integer commentId, String userName) {

        log.info("Reading comment {}", commentId);

        CommentApiDTO commentDTO = commentMapper.toDTO(commentRepository.findCommentById(commentId));
        Integer projectId = issueRepository.findById(commentDTO.getIssueId()).get()
                .getProject().getId();

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }
        return commentDTO;

    }

    public CommentApiDTO createComment(CommentApiDTO commentDTO, String username) {

        log.info("Creating for user {}", username);

        Comment comment = commentRepository.save(commentMapper.toEntity(commentDTO));

        return commentMapper.toDTO(comment);
    }

    public CommentApiDTO updateComment(CommentApiDTO commentDTO, String userName) {
        Integer issueId = commentDTO.getIssueId();
        Integer projectId = issueRepository.findIssueById(issueId).getProject().getId();

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }

        Comment updatedComment = commentRepository.save(commentMapper.toEntity(commentDTO));

        return commentMapper.toDTO(updatedComment);
    }

    public void deleteComment(Integer commentId, String username) {
        Comment comment = commentRepository.findCommentById(commentId);
        Integer projectId = issueRepository.findById(comment.getIssue().getId()).get()
                .getProject().getId();

        if (!userProjectService.canThisUserReadThisProject(username, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }

        commentRepository.delete(comment);
    }
}
