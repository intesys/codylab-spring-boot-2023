package it.intesys.academy.controller.rest;
import it.intesys.academy.dto.CommentDTO;
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
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {this.commentService = commentService;}

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getComments(
            @RequestParam Integer issueId, @RequestHeader(name = "X-User-Name") String username) {

        return ResponseEntity.ok(commentService.readCommentsByIssueId(issueId, username));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable int commentId,
                                                 @RequestHeader(name = "X-User-Name") String username) {

        return ResponseEntity.ok(commentService.readComments(commentId, username));
    }


    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                  @RequestHeader(name = "X-User-Name") String username) {
        if (commentDTO.getId() != null) {
            log.error("Bad request, id must be null when creating a new comment");
            return ResponseEntity.badRequest().build();
        }

        if (!StringUtils.hasText(commentDTO.getText())) {
            throw new RuntimeException("Invalid DTO");
        }

        return ResponseEntity.ok(commentService.createComment(commentDTO, username));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable int commentId, @RequestBody CommentDTO commentDTO,
                                                @RequestHeader(name = "X-User-Name") String username) {
        if (commentDTO.getId() == null) {
            log.error("Bad request, id must not be null when updating a comment");
            return ResponseEntity.badRequest().build();
        }
        if (commentDTO.getId() != commentId) {
            log.error("Bad request, id in path and in body must be the same");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(commentService.updateComment(commentDTO, username));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId,
                                            @RequestHeader("X-User-Name") String username) {
        commentService.deleteComment(commentId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
