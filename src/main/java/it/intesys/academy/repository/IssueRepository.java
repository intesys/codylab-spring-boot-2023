package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Issues;
import jakarta.persistence.Entity;
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
public class IssueRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final EntityManager em;

    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {

        this.jdbcTemplate = jdbcTemplate;
        this.em = em;
    }

    public List<Issues> readIssues(List<Integer> projectIds) {
        return em.createQuery("from Issues where projectid in :projectIds",Issues.class)
                .setParameter("projectIds",projectIds)
                .getResultList();
    }

    public Issues getIssue(Integer projectIds) {
        return em.createQuery("from Issues where projectid = :projectIds",Issues.class)
                .setParameter("projectIds",projectIds)
                .getSingleResult();
    }

    public Issues readIssue(Integer issueId) {
        return em.find(Issues.class,issueId);
    }

    public Issues createIssue(Issues issue){
        em.persist(issue);
        em.flush();
        return issue;
    }

    public void updateIssue(Issues issue){
        em.merge(issue);
    }

    public void deleteIssue(Integer issueId){
        em.remove(readIssue(issueId));
    }
}
