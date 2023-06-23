package it.intesys.academy.repository;

import it.intesys.academy.domain.Comment;
import it.intesys.academy.domain.Issue;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends ListCrudRepository<Comment, Integer>, ListPagingAndSortingRepository<Comment, Integer> {
    List<Comment> findCommentsByIssueIdIn(List<Integer> ids);

    List<Comment> findCommentsByIssueId(Integer issueId);
    Comment findCommentById(Integer commentId);
}
