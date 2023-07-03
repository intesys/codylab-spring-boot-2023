package it.intesys.academy.repository;

import it.intesys.academy.domain.Project;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Integer>, ListPagingAndSortingRepository<Project, Integer> {

    List<Project> findByIdIn(List<Integer> ids);
    List<Project> findAll();


}
