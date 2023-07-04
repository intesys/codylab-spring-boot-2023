package it.intesys.academy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="person_project")
public class PersonProject {

    @ManyToOne
    private Person person;

    @ManyToOne
    private Project project;
}
