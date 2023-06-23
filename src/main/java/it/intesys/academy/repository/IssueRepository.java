package it.intesys.academy.repository;

import it.intesys.academy.domain.Issue;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends ListCrudRepository<Issue, Integer>, ListPagingAndSortingRepository<Issue, Integer> {
    public List<Issue> findByProject_Id(Integer id);
    Issue findIssueById(Integer issueId);
}
