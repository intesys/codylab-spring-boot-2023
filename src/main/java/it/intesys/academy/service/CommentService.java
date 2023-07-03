package it.intesys.academy.service;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.controller.rest.errors.ForbiddenException;
import it.intesys.academy.domain.Comment;
import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);


    private final CommentRepository commentRepository;

    private final UserProjectService userProjectService;

    private final CommentMapper commentMapper;
    private final IssueRepository issueRepository;

    public CommentService(IssueRepository issueRepository, CommentRepository commentRepository, UserProjectService userProjectService, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
        this.commentMapper = commentMapper;
        this.issueRepository = issueRepository;
    }

    public List<CommentApiDTO> readCommentsByIssueId(Integer issueId) {

        return commentRepository.findByIssue_Id(issueId)
                .stream()
                .map(commentMapper::toApiDto)
                .collect(Collectors.toList());

    }




    public CommentApiDTO createComment(CommentApiDTO commentApiDTO) {



        Comment comment = commentRepository.save(commentMapper.toEntity(commentApiDTO));

        return commentMapper.toApiDto(comment);
    }

    public CommentApiDTO updateComment(CommentApiDTO commentApiDTO) {

        var userName = SecurityUtils.getCurrentUser();

        if (!userProjectService.canThisUserReadThisProject(userName, issueRepository.findIssueById(commentApiDTO.getIssueId()).getProject().getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Comment dbComment = commentRepository.findCommentById(commentApiDTO.getId());
        if ( dbComment.getIssue().getId() != commentApiDTO.getIssueId()) {
            throw new RuntimeException("Security constraints violation");
        }

        Comment updatedComment = commentRepository.save(commentMapper.toEntity(commentApiDTO));

        return commentMapper.toApiDto(updatedComment);
    }

    public void deleteComment(Integer commentId) {

        Comment dbComment = commentRepository.findCommentById(commentId);

        if (!SecurityUtils.hasAuthority("ROLE_ADMIN")) {
            throw new ForbiddenException("Cannot delete comment");
        }

        commentRepository.deleteById(dbComment.getId());
    }

}
