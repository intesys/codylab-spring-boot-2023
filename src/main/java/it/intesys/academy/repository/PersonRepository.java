package it.intesys.academy.repository;

import it.intesys.academy.domain.Person;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

    public interface PersonRepository extends ListCrudRepository<Person, Integer>, ListPagingAndSortingRepository<Person, Integer> {
        public Person findPersonByUsername(String username);
}
