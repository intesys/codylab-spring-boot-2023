package it.intesys.academy.repository;

import it.intesys.academy.entity.Issue;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    public List<Issue> readIssues(List<Integer> projectIds) {

        return em.createQuery("from Issue i join fetch i.project p where p.id in (:projectIds)", Issue.class)
                .setParameter("projectIds", projectIds)
                .getResultList();

    }

    public Issue readIssue(Integer issueId) {
       return em.find(Issue.class, issueId);
       /* return em.createQuery("from Issue where id = :issueId", Issue.class)
                .setParameter("issueId", issueId)
                .getSingleResult();*/

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

        jdbcTemplate.update("delete from Comment where issueId = :issueId",
                Map.of("issueId", issueId));

       em.createQuery("delete from Issue where id = :issueId")
                .setParameter("issueId", issueId)
                .executeUpdate();
    }

}
