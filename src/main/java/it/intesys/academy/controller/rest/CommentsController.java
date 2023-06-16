package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.service.CommentService;
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
    public CommentDTO getComment(@PathVariable Integer issueId,
                                 @PathVariable Integer commentId,
                                 @RequestParam String username){
        return commentService.getComment(issueId,username,commentId);
    }

    @PostMapping("/comments")
    public CommentDTO postComment(@RequestBody CommentDTO commentDTO,
                                  @RequestParam String username){
        return commentService.createComment(commentDTO, username);
    }
}
