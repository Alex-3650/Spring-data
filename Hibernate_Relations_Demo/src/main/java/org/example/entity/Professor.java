package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "proffesors")
public class Professor extends Person {

    @Column(name = "salary")
    private double salary;

    @Column(name = "department")
    private String department;


    @Override
    public String getRole() {
        return "professor";
    }

    public Professor(String firstName, String lastName, String email, double salary, String department) {
        super(firstName, lastName, email);
        this.salary = salary;
        this.department = department;
    }

    public Professor() {

    }
}
