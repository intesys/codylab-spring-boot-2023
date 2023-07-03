package it.intesys.academy.controller.rest;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.controller.rest.errors.BadRequestException;
import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final static Logger log = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/comments")
    public ResponseEntity<CommentApiDTO> createComment(@RequestBody CommentApiDTO commentApiDTO) {
        if (commentApiDTO.getId() != null) {
            log.error("Bad request, id must be null when creating a new issue");
            throw new BadRequestException("Id must be null when creating a new comment");
        }

        if (!StringUtils.hasText(commentApiDTO.getText())) {
            throw new BadRequestException("Invalid DTO");
        }

        return ResponseEntity.ok(commentService.createComment(commentApiDTO));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentApiDTO> updateComment(@PathVariable int commentId, @RequestBody CommentApiDTO commentApiDTO) {
        if (commentApiDTO.getId() == null) {

            throw new BadRequestException("Id must be evaluated when searching a comment");
        }
        if (commentApiDTO.getId() != commentId) {
            throw new BadRequestException("Bad request, id in path and in body must be the same");
        }

        return ResponseEntity.ok(commentService.updateComment(commentApiDTO));
    }
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
