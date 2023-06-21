package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import jakarta.persistence.EntityManager;
import it.intesys.academy.entity.Issue;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository
public class  IssueRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EntityManager em;
    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {
        this.em= em;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Issue> readIssues(List<Integer> projectIds) {

        return em.createQuery("from Issue i join fetch i.project p where p.id in (:projectIds)", Issue.class)
                .setParameter("projectIds", projectIds)
                .getResultList();
    }

    public Issue readIssue(Integer issueId) {
        return em.find(Issue.class, issueId);

    }

    public Issue createIssue(Issue issue) {

        em.persist(issue);
        em.flush();
        return issue;
    }

    public Issue updateIssue(Issue issue) {
        return em.merge(issue);
    }


    public void deleteIssue(Integer issueId) {

        em.createQuery("delete from Issue where id = :issueId")
                .setParameter("issueId", issueId)
                .executeUpdate();
    }

}
