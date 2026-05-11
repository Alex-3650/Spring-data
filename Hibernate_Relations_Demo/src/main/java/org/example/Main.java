package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("base");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person student = new Student("Peter","Ivanov","peter@sof.edu","A23",4.5);
        em.persist(student);

        Person professor = new Professor("John","Doe","john@sof.edu",2342,"Sales");
        em.persist(professor);

        Person fullTimeProfessor = new FullTimeProfessor("Robert","Dean","robert@sof.edu",2342,"Sales",4);
        em.persist(fullTimeProfessor);

        Person partTimeProfessor = new PartTimeProfessor("Jane","Layn","jane@sof.edu",5542,"Sales",21);
        em.persist(partTimeProfessor);

        em.getTransaction().commit();
    }
}