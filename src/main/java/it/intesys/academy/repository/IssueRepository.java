package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
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

    public Integer createIssue(IssueDTO issueDTO){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("nome", issueDTO.getNome())
                .addValue("descrizione", issueDTO.getDescrizione())
                .addValue("author", issueDTO.getAuthor())
                .addValue("projectId", issueDTO.getProjectId());
        jdbcTemplate.update("INSERT INTO ISSUES (nome,descrizione,author,projectId) VALUES (:nome,:descrizione, :author, :projectId)",
                parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

}
