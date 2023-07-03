package it.intesys.academy.controller.rest;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class CommentController {
    private final static Logger log = LoggerFactory.getLogger(IssueController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentApiDTO>> getComments(@RequestParam Integer issueId) {

        return ResponseEntity.ok(commentService.readCommentsByIssueId(issueId));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentApiDTO> getComment(@PathVariable int commentId) {

        return ResponseEntity.ok(commentService.readComments(commentId));
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentApiDTO> createComment(@RequestBody CommentApiDTO commentDTO) {
        if (commentDTO.getId() != null) {
            log.error("Bad request, id must be null when creating a new issue");
            return ResponseEntity.badRequest().build();
        }

        if (!StringUtils.hasText(commentDTO.getDescription())) {
            throw new RuntimeException("Invalid DTO");
        }

        return ResponseEntity.ok(commentService.createComment(commentDTO));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentApiDTO> updateComment(@PathVariable int commentId, @RequestBody CommentApiDTO commentDTO) {
        if (commentDTO.getId() == null) {
            log.error("Bad request, id must not be null when updating a issue");
            return ResponseEntity.badRequest().build();
        }
        if (commentDTO.getId() != commentId) {
            log.error("Bad request, id in path and in body must be the same");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(commentService.updateComment(commentDTO));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
