package it.intesys.academy.domain;

import jakarta.persistence.Column;

import java.io.Serializable;

public class ComposedId implements Serializable {
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "employee_number")
    private Long employeeNumber;
}
