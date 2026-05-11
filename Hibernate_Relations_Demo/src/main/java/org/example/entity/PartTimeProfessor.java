package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "part_time_professors")
public class PartTimeProfessor extends Professor {

    @Column(name = "hours_per_week")
    private int hoursPerWeek;


    @Override
    public String getRole() {
        return "part_time_professor";
    }

    public PartTimeProfessor(String firstName, String lastName, String email, double salary, String department, int hoursPerWeek) {
        super(firstName, lastName, email, salary, department);
        this.hoursPerWeek = hoursPerWeek;
    }

    public PartTimeProfessor() {
    }
}
