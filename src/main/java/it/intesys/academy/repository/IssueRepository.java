package it.intesys.academy.repository;

import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.IssueDTO;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends ListCrudRepository<Issue, Integer>, ListPagingAndSortingRepository<Issue, Integer> {
    Issue findIssueById(Integer issueId);

}
