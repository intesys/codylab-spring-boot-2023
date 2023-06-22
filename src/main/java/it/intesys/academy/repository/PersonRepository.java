package it.intesys.academy.repository;

import it.intesys.academy.domain.Person;
import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.UserProject;
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
public interface PersonRepository extends ListCrudRepository<Person, Integer>, ListPagingAndSortingRepository<Person, Integer> {
    public Person findPersonByUsername(String username);
}