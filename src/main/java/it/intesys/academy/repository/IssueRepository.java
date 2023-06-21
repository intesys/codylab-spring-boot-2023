package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository
public class IssueRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IssueDTO> readIssues(List<Integer> projectIds) {

        List<IssueDTO> issues =
            jdbcTemplate.query("SELECT id, name, description, author, projectId FROM Issue WHERE projectId in (:projectIds)",

                               Map.of("projectIds", projectIds),

                               BeanPropertyRowMapper.newInstance(IssueDTO.class));

        return issues;
    }

    public IssueDTO readIssue(Integer issueId) {

        return jdbcTemplate.queryForObject("SELECT id, name, description, author, projectId FROM Issue WHERE id = :issueId",

                Map.of("issueId", issueId),

                BeanPropertyRowMapper.newInstance(IssueDTO.class));

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
