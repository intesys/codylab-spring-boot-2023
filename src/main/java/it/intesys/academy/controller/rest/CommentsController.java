package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentsController {
    private final CommentService commentService;

    public CommentsController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("issues/{issueId}/comments")
    public List<CommentDTO> getComments(@PathVariable Integer issueId,
                                        @RequestParam String username){
        return commentService.getComments(issueId,username);
    }

    @GetMapping("/issues/{issueId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Integer issueId,
                                                @PathVariable Integer commentId,
                                                @RequestParam String username){
        return ResponseEntity.ok(commentService.getComment(issueId,username,commentId));
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> postComment(@RequestBody CommentDTO commentDTO,
                                  @RequestParam String username){
        return ResponseEntity.ok(commentService.createComment(commentDTO, username));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> putComment(@RequestBody CommentDTO commentDTO,
                                                 @RequestParam String username,
                                                 @PathVariable Integer commentId){
        return ResponseEntity.ok(commentService.updateComment(commentDTO, username, commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId,
                              @RequestParam String username){
        commentService.deleteComment(commentId, username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
