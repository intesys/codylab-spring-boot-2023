package it.intesys.academy.repository;

import it.intesys.academy.controller.openapi.model.CommentApiDTO;
import it.intesys.academy.domain.Comment;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends ListCrudRepository<Comment, Integer>, ListPagingAndSortingRepository<Comment, Integer> {
    Comment findCommentById(Integer commentId);
    List<CommentApiDTO> findCommentByIssue_Id(Integer issueId);
}