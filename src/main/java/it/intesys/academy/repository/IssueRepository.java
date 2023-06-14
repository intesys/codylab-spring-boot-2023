package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
            jdbcTemplate.query("SELECT id, nome, descrizione, author, projectId FROM Issues WHERE projectId in (:projectIds)",

                               Map.of("projectIds", projectIds),

                               BeanPropertyRowMapper.newInstance(IssueDTO.class));

        return issues;
    }

    public IssueDTO getIssue(Integer projectIds) {

        IssueDTO issues =
                jdbcTemplate.queryForObject("SELECT id, nome, descrizione, author, projectId FROM Issues WHERE projectId = :projectIds",

                        Map.of("projectIds", projectIds),

                        BeanPropertyRowMapper.newInstance(IssueDTO.class));

        return issues;
    }

    public IssueDTO readIssue(Integer issueId) {

        IssueDTO issues =
                jdbcTemplate.queryForObject("SELECT id, nome, descrizione, author, projectId FROM Issues WHERE id in (:issue)",

                        Map.of("issue", issueId),

                        BeanPropertyRowMapper.newInstance(IssueDTO.class));

        return issues;
    }



}
