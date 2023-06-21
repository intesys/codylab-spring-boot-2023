package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.entity.Issue;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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


        return em.createQuery("from Issue where projectId in (:projectIds)", Issue.class)
                .setParameter("projectIds", projectIds)
                .getResultList();


    }

    public Issue readIssue(Integer issueId) {

    return em.createQuery("from Issue where id = :issueId", Issue.class).setParameter("issueId", issueId).getSingleResult();

    }

    public Integer createIssue(IssueDTO issueDTO) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        var params = new MapSqlParameterSource()
                .addValue("name", issueDTO.getName())
                .addValue("description", issueDTO.getDescription())
                .addValue("author", issueDTO.getAuthor())
                .addValue("projectId", issueDTO.getProjectId());

        jdbcTemplate.update("INSERT INTO Issue (name, description, author, projectId) VALUES (:name, :description, :author, :projectId)",
                params, keyHolder);

            return keyHolder.getKey().intValue();
    }

    public void updateIssue(IssueDTO issueDTO) {

            var params = new MapSqlParameterSource()
                    .addValue("name", issueDTO.getName())
                    .addValue("description", issueDTO.getDescription())
                    .addValue("projectId", issueDTO.getProjectId())
                    .addValue("issueId", issueDTO.getId());

            jdbcTemplate.update("UPDATE Issue SET name = :name, description = :description,  projectId = :projectId WHERE id = :issueId",
                    params);
    }

    public void deleteIssue(Integer issueId) {

        jdbcTemplate.update("delete from Comment where issueId = :issueId",
                Map.of("issueId", issueId));

        jdbcTemplate.update("DELETE FROM Issue WHERE id = :issueId",
                Map.of("issueId", issueId));
    }

}
