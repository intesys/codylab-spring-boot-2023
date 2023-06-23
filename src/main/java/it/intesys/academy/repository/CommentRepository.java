package it.intesys.academy.repository;

import it.intesys.academy.domain.Comment;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.dto.CommentDTO;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentRepository extends ListCrudRepository<Comment, Integer>, ListPagingAndSortingRepository<Comment, Integer> {
    public List<Comment> findCommentsByIssueId(Integer issueId);

    public Comment findCommentById(Integer commentId);
}
