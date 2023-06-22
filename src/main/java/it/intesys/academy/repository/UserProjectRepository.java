package it.intesys.academy.repository;

import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.UserProject;
import it.intesys.academy.dto.UserProjectDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface  UserProjectRepository extends ListCrudRepository<UserProject, Integer>, ListPagingAndSortingRepository<UserProject, Integer> {

    public List<UserProject> findUserProjectByPersonUsernameAndProjectId(String username, Integer projectId);

    public List<UserProject> findUserProjectsByPersonUsername(String username);



}
