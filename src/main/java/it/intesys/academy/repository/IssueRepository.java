package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.entity.Issue;
import it.intesys.academy.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class IssueRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EntityManager em;

    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {

        this.jdbcTemplate = jdbcTemplate;
        this.em = em;
    }

    public List<Issue> readIssues(List<Integer> projectIds) {


        return em.createQuery("from Issue where projectId in (:projectIds)", Issue.class)
                .setParameter("projectIds", projectIds)
                .getResultList();


    }

    public Issue readIssue(Integer issueId) {

    return em.createQuery("from Issue where id = :issueId", Issue.class).setParameter("issueId", issueId).getSingleResult();

    }

    public Issue createIssue(Issue issue) {

        em.persist(issue);
        em.flush();
        return issue;
    }

    public void updateIssue(Issue issue) {

            em.merge(issue);
    }

    public void deleteIssue(Integer issueId) {
        em.createNativeQuery("DELETE FROM Comment c WHERE issueId =:issueId", Issue.class)
                .setParameter("issueId", issueId)
                .executeUpdate();

        em.createNativeQuery("DELETE FROM Issue WHERE id = :issueId", Issue.class)
                .setParameter("issueId", issueId)
                .executeUpdate();

    }

}
