package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "full_time_professors")
public class FullTimeProfessor extends Professor {

    @Column(name = "office_number", nullable = false)
    private int officeNumber;

    @Override
    public String getRole() {
        return "full_time_professor";
    }


    public FullTimeProfessor(String firstName, String lastName, String email, double salary, String department, int officeNumber) {
        super(firstName, lastName, email, salary, department);
        this.officeNumber = officeNumber;
    }

    public FullTimeProfessor() {

    }
}
