package org.example.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name",unique = true, nullable = false)
    private String name;

    @Column(name = "country_name", nullable = false)
    private String countryName;

     @OneToMany(mappedBy = "town")
     private Set <Author> author = new HashSet<Author>();


     public void addAuthor(Author author) {
         this.author.add(author);
         author.setTown(this);
     }

    protected Town() {
    }

    public Town(String name, String countryName) {
        this.name = name;
        this.countryName = countryName;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Set<Author> getAuthor() {
        return author;
    }

    public void setAuthor(Set<Author> author) {
        this.author = author;
    }
}
