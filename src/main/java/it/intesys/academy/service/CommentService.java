package it.intesys.academy.service;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.controller.rest.errors.ForbiddenException;
import it.intesys.academy.domain.Comment;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {

    private static final Logger log= LoggerFactory.getLogger(CommentService.class);
    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;

    public CommentService(CommentMapper commentMapper, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }


    public List<CommentApiDTO> readCommentsByIssueId(Integer issueId){
        return commentRepository.findCommentByIssue_Id(issueId);

    }



    public CommentApiDTO createComment(CommentApiDTO commentApiDTO){
        var username = SecurityUtils.getCurrentUser();
        log.info("Creating for user {}", username);

        Comment comment = commentRepository.save(commentMapper.toEntity(commentApiDTO));

        return commentMapper.toApiDto(comment);

    }


    public void deleteComment(Integer commentId){
        var username = SecurityUtils.getCurrentUser();
        Comment dbComment= commentRepository.findCommentById(commentId);
        if (!SecurityUtils.hasAuthority("ROLE_ADMIN")) {
            throw new ForbiddenException("Cannot delete comment");
        }
        commentRepository.deleteById(commentId);
    }
}
