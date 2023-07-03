package it.intesys.academy.controller.openapi;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.controller.rest.errors.BadRequestException;
import it.intesys.academy.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CommentApiDelegateImpl implements CommentsApiDelegate{
    private final CommentService commentService;

    public CommentApiDelegateImpl(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    public ResponseEntity<CommentApiDTO> createComment(CommentApiDTO commentApiDTO) {
        if (commentApiDTO.getId() != null) {
            throw new BadRequestException("Id must be null when creating a new comment");
        }
        if (!StringUtils.hasText(commentApiDTO.getText())) {
            throw new BadRequestException("Invalid DTO");
        }

        return ResponseEntity.ok(commentService.createComment(commentApiDTO));
    }

    @Override
    public ResponseEntity<Void> deleteComment(Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
