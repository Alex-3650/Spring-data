package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends Person {

    @Column(name = "faculty_number", nullable = false, length = 10)
    private String facultyNumber;

    @Column(name = "gpa")
   private double gpa;

    public Student(String firstName, String lastName, String email, String facultyNumber, double gpa) {
        super(firstName, lastName, email);
        this.facultyNumber = facultyNumber;
        this.gpa = gpa;
    }

    public Student() {

    }

    public String getRole() {
        return "student";
    }


}
