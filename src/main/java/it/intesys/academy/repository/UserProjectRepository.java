package it.intesys.academy.repository;
import it.intesys.academy.domain.UserProject;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRepository extends ListCrudRepository<UserProject, Integer>, ListPagingAndSortingRepository<UserProject, Integer> {
public List<UserProject> findUserProjectsByProjectIdAndPersonUsername(Integer projectId, String username);
public List<UserProject> findUserProjectsByPersonUsername(String username);
    }

