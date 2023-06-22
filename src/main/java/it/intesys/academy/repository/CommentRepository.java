package it.intesys.academy.repository;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.entity.Comments;
import jakarta.persistence.EntityManager;
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

     private final EntityManager em;

     public CommentRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em){
         this.jdbcTemplate = jdbcTemplate;
         this.em = em;
     }

     public List<Comments> getComments(Integer issueId){
         return em.createQuery("from Comments where issue.id = :issueId", Comments.class)
                 .setParameter("issueId",issueId)
                 .getResultList();
     }

     public Comments getComment(Integer issueId){
         return em.createQuery("from Comments where issue.id = :issueId", Comments.class)
                 .setParameter("issueId",issueId)
                 .getSingleResult();
     }

    public Comments readComment(Integer commentId){
        return em.find(Comments.class,commentId);
    }
    public Comments insertComment(Comments comment){
        em.persist(comment);
        em.flush();
        return comment;
    }

    public Comments updateComment(Comments comment){
         return em.merge(comment);
    }

    public void removeComment(Integer commentId){
         em.remove(readComment(commentId));
    }

}
