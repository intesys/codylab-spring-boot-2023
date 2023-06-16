package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommentRepository {
     private final NamedParameterJdbcTemplate jdbcTemplate;

     public CommentRepository(NamedParameterJdbcTemplate jdbcTemplate){
         this.jdbcTemplate = jdbcTemplate;
     }

     public List<CommentDTO> getComments(Integer issueId){
         return jdbcTemplate.query("SELECT id, descrizione, author,issueId FROM Comments where issueId = :issueId",
                 Map.of("issueId",issueId),
                 BeanPropertyRowMapper.newInstance(CommentDTO.class));
     }

     public CommentDTO getComment(Integer issueId){
         return jdbcTemplate.queryForObject("SELECT id, descrizione, author, issueId FROM Comments where issueId = :issueId",
                 Map.of("issueId",issueId),
                 BeanPropertyRowMapper.newInstance(CommentDTO.class));
     }

    public CommentDTO readComment(Integer commentId){
        return jdbcTemplate.queryForObject("SELECT id, descrizione, author, issueId FROM Comments where id = :commentId",
                Map.of("commentId",commentId),
                BeanPropertyRowMapper.newInstance(CommentDTO.class));
    }

    public Integer insertComment(CommentDTO commentDTO){
        KeyHolder key = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("author", commentDTO.getAuthor())
                .addValue("descrizione", commentDTO.getDescrizione())
                .addValue("issueId", commentDTO.getIssueId());
         jdbcTemplate.update("INSERT INTO COMMENTS(author, descrizione, issueId) values (:author, :descrizione, :issueId)",
                 parameterSource,
                 key);
         return key.getKey().intValue();
    }

}
