package it.intesys.academy.repository;

import it.intesys.academy.domain.Project;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;

public interface ProjectRepository extends ListCrudRepository<Project, Integer>,
                                           ListPagingAndSortingRepository<Project, Integer> {

    public List<Project> findAllByIdIn(List<Integer> ids);

}
