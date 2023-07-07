package it.intesys.academy.mapper;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.domain.Comment;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentApiDTO CommentApiDTO) {
        Comment Comment = new Comment();
        Comment.setId(CommentApiDTO.getId());
        Comment.setText(CommentApiDTO.getText());
        Comment.setAuthor(CommentApiDTO.getAuthor());
        Issue Issue = new Issue();
        Issue.setId(CommentApiDTO.getIssueId());
        Comment.setIssue(Issue);
        return Comment;
    }

    public CommentDTO toDto(Comment Comment) {
        CommentDTO CommentDTO = new CommentDTO();
        CommentDTO.setId(Comment.getId());
        CommentDTO.setText(Comment.getText());
        CommentDTO.setAuthor(Comment.getAuthor());
        CommentDTO.setIssueId(Comment.getIssue().getId());
        return CommentDTO;
    }

    public CommentApiDTO toApiDto(Comment Comment) {
        CommentApiDTO CommentApiDTO = new CommentApiDTO();
        CommentApiDTO.setId(Comment.getId());
        CommentApiDTO.setText(Comment.getText());
        CommentApiDTO.setAuthor(Comment.getAuthor());
        CommentApiDTO.setIssueId(Comment.getIssue().getId());
        return CommentApiDTO;
    }
}
