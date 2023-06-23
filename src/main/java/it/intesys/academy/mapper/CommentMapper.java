package it.intesys.academy.mapper;

import it.intesys.academy.domain.Comment;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        comment.setAuthor(commentDTO.getAuthor());
        Issue issue = new Issue();
        issue.setId(commentDTO.getIssueId());
        comment.setIssue(issue);
        return comment;
    }

    public CommentDTO toDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setAuthor(comment.getAuthor());
        commentDTO.setIssueId(comment.getIssue().getId());
        return commentDTO;
    }
}
