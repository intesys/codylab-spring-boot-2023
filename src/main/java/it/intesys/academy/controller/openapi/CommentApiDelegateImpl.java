package it.intesys.academy.controller.openapi;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentApiDelegateImpl implements CommentsApiDelegate{
    private final CommentService commentService;

    public CommentApiDelegateImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<Void> commentsCommentIdDelete(Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentApiDTO> commentsCommentIdPut(Integer commentId, CommentApiDTO commentApiDTO) {
        return ResponseEntity.ok(commentService.updateComment(commentApiDTO));
    }

    @Override
    public ResponseEntity<CommentApiDTO> createComment(CommentApiDTO commentApiDTO) {
        return ResponseEntity.ok(commentService.createComment(commentApiDTO));
    }

    @Override
    public ResponseEntity<CommentApiDTO> getComment(Integer commentId) {
        return ResponseEntity.ok(commentService.readComments(commentId));
    }

    @Override
    public ResponseEntity<CommentApiDTO> getComments(Integer commentId) {
        return ResponseEntity.ok(commentService.readComments(commentId));
    }
}
