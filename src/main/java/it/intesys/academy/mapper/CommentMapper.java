package it.intesys.academy.mapper;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.domain.Comment;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.dto.CommentDTO;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

@Component
public class CommentMapper {
    public CommentApiDTO toDTO(Comment comment){
        CommentApiDTO commentDTO = new CommentApiDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDescription(comment.getDescription());
        commentDTO.setAuthor(comment.getAuthor());
        commentDTO.setIssueId(comment.getIssue().getId());
        return commentDTO;
    }

    public Comment toEntity(CommentApiDTO commentDTO){
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setDescription(commentDTO.getDescription());
        comment.setAuthor(commentDTO.getAuthor());
        Issue issue = new Issue();
            issue.setId(commentDTO.getIssueId());
        comment.setIssue(issue);
        return comment;
    }
}
