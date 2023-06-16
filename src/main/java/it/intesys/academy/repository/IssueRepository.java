package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
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

    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IssueDTO> readIssues(List<Integer> projectIds) {

        List<IssueDTO> issues =
            jdbcTemplate.query("SELECT id, name, description, author, projectId FROM Issues WHERE projectId in (:projectIds)",

                               Map.of("projectIds", projectIds),

                               BeanPropertyRowMapper.newInstance(IssueDTO.class));

        return issues;
    }

    public IssueDTO readIssue(Integer issueId) {

        return jdbcTemplate.queryForObject("SELECT id, name, description, author, projectId FROM Issues WHERE id = :issueId",

                Map.of("issueId", issueId),

                BeanPropertyRowMapper.newInstance(IssueDTO.class));

    }

    public Integer createIssue(IssueDTO IssueDTO) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource(Map.of(
                "name", IssueDTO.getName(), "description", IssueDTO.getDescription(), "author", IssueDTO.getAuthor(), "projectId", IssueDTO.getProjectId())
        );

        jdbcTemplate.update("insert into Issues (name, description, author, projectId) values (:name, :description, :author, :projectId)",
                parameterSource, keyHolder);

        return keyHolder.getKey().intValue();



    }

    public void updateIssue (IssueDTO issueDTO)
    {
        jdbcTemplate.update("update Issues set name=:name, description=:description where id=:issueId",
                Map.of("name",issueDTO.getName(), "description", issueDTO.getDescription(), "issueId", issueDTO.getId()));


    }

    public void deleteIssue(Integer issueId) {

        jdbcTemplate.update("delete from Issues where Id = :Id", Map.of("Id", issueId));

    }
}
