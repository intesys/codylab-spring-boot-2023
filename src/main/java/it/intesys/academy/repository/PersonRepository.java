package it.intesys.academy.repository;

import it.intesys.academy.domain.Person;
import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.UserProject;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;

public interface PersonRepository extends ListCrudRepository<Person, Integer>, ListPagingAndSortingRepository<Person, Integer> {
    public Person findByUsername(String username);

}
