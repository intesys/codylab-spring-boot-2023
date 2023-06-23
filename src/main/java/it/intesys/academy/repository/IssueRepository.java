package it.intesys.academy.repository;

import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Person;
import jakarta.persistence.EntityManager;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IssueRepository extends ListCrudRepository<Issue, Integer>, ListPagingAndSortingRepository<Issue, Integer> {
    public Issue findIssueById(Integer issueId);

    public List<Issue> findByProjectIdIn(List<Integer> projectIds);

}
