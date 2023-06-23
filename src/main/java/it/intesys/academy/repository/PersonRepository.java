package it.intesys.academy.repository;

import it.intesys.academy.domain.Person;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface PersonRepository extends ListCrudRepository<Person, Integer>, ListPagingAndSortingRepository<Person, Integer> {
        Person findPersonByUsername(String username);
}
