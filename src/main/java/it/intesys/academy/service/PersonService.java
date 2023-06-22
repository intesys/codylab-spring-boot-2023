package it.intesys.academy.service;

import it.intesys.academy.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Integer getUserIdByUsername(String username){
        return personRepository.findByUsername(username).getId();
    }
}
