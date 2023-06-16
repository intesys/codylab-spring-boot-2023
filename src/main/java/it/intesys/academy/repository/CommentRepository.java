package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CommentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CommentDTO> findCommentsByIssueId(Integer issueId) {
        List<CommentDTO> comments =
                jdbcTemplate.query("SELECT id, description, author, issueId FROM Comments WHERE issueId = :issueId",

                        Map.of("issueId", issueId),

                        BeanPropertyRowMapper.newInstance(CommentDTO.class));

        return comments;

    }
}
