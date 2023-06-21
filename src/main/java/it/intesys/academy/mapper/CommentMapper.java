package it.intesys.academy.mapper;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.entity.Comments;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comments toEntity(CommentDTO commentDTO){
        Comments comment = new Comments();
        comment.setId(commentDTO.getId());
        comment.setDescription(commentDTO.getDescription());
        comment.setAuthor(commentDTO.getAuthor());
        comment.setIssueId(commentDTO.getIssueId());
        return comment;
    }

    public CommentDTO toDTO(Comments comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDescription(comment.getDescription());
        commentDTO.setAuthor(comment.getAuthor());
        commentDTO.setIssueId(comment.getIssueId());
        return commentDTO;
    }
}
