package it.intesys.academy.service;

import it.intesys.academy.domain.Comment;
import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserProjectService userProjectService;

    private final IssueRepository issueRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, UserProjectService userProjectService, IssueRepository issueRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userProjectService = userProjectService;
        this.issueRepository = issueRepository;
    }

    private final Logger log = LoggerFactory.getLogger(CommentService.class);


    public List<CommentDTO> readCommentsByIssueId(Integer issueId, String userName) {

        log.info("Reading comments for issue {}", issueId);
        Integer projectId = issueRepository.findById(issueId).get()
                .getProject().getId();

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }

        return commentRepository.findCommentsByIssueId(issueId).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    public CommentDTO readComments(Integer commentId, String userName) {

        log.info("Reading comment {}", commentId);

        CommentDTO commentDTO = commentMapper.toDto(commentRepository.findCommentById(commentId));
        Integer projectId = issueRepository.findById(commentDTO.getIssueId()).get()
                .getProject().getId();

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }
        return commentDTO;

    }


    public CommentDTO createComment(CommentDTO commentDTO, String username) {

        log.info("Creating for user {}", username);


        Comment comment = commentRepository.save(commentMapper.toEntity(commentDTO));

        return commentMapper.toDto(comment);
    }

    public CommentDTO updateComment(CommentDTO commentDTO, String userName) {

        if (!userProjectService.canThisUserReadThisProject(userName, commentDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Comment dbcomment = commentRepository.findCommentById(commentDTO.getId());
        if ( dbcomment.getIssueid().getId() != commentDTO.getIssueId() &&
                !userProjectService.canThisUserReadThisProject(userName, dbcomment.getIssueid().getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Comment updatedcomment = commentRepository.save(commentMapper.toEntity(commentDTO));

        return commentMapper.toDto(updatedcomment);
    }

    public void deleteComment(Integer commentId, String username) {
        Comment dbcomment = commentRepository.findCommentById(commentId);
        if (!userProjectService.canThisUserReadThisProject(username, dbcomment.getIssueid().getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        commentRepository.delete(dbcomment);
    }
}
