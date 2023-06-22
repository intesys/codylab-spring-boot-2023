package it.intesys.academy.mapper;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.entity.Comments;
import it.intesys.academy.entity.Issues;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comments toEntity(CommentDTO commentDTO){
        Comments comment = new Comments();
        comment.setId(commentDTO.getId());
        comment.setDescription(commentDTO.getDescription());
        comment.setAuthor(commentDTO.getAuthor());
        Issues issues = new Issues();
        issues.setId(commentDTO.getIssueId());
        comment.setIssue(issues);
        return comment;
    }

    public CommentDTO toDTO(Comments comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDescription(comment.getDescription());
        commentDTO.setAuthor(comment.getAuthor());
        commentDTO.setIssueId(comment.getIssue().getId());
        return commentDTO;
    }
}
