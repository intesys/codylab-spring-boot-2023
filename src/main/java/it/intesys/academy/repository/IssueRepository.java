package it.intesys.academy.repository;

import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Project;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IssueRepository extends ListCrudRepository<Issue, Integer>, ListPagingAndSortingRepository<Issue, Integer> {
    List<Issue> findByProjectIdIn(List<Integer> ids);
    Issue findIssueById(Integer issueId);
}
