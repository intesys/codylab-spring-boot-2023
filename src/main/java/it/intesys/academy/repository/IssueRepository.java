package it.intesys.academy.repository;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class IssueRepository {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public IssueRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Legge tutte le Issues
     * @return
     */
    public List<IssueDTO> readIssues(){
        List<IssueDTO> projects = jdbcTemplate.query("SELECT id, nome, descrizione, author, projectId FROM Issues",
                BeanPropertyRowMapper.newInstance(IssueDTO.class));
        return projects;
    }

    /**
     * Restituisce una lista di Issues in base alla lista di id passata
     * @param issuesIds
     * @return
     */
    public List<IssueDTO> searchIssues(List<Integer> issuesIds){
        List<IssueDTO> issues = jdbcTemplate.query("SELECT id, nome, descrizione, author,projectId FROM Issues where id in (:issuesIds)",

                Map.of("issuesIds", issuesIds),

                BeanPropertyRowMapper.newInstance(IssueDTO.class));
        return issues;
    }

    /**
     * Legge le Issues in base al progetto
     * @param projectIds
     * @return
     */
    public List<IssueDTO> readIssuesForProject(Integer projectIds){
        List<IssueDTO> issues = jdbcTemplate.query("SELECT id,nome,descrizione,author,projectId FROM Issues WHERE projectId = :projectIds",
                Map.of("projectIds",projectIds),
                BeanPropertyRowMapper.newInstance(IssueDTO.class));
        return issues;
    }

}
