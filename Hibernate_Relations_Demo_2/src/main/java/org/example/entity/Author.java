package org.example.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "birth_year",nullable = false)
    private int birthYear;

    @OneToMany(mappedBy = "author",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<Book>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "town_id",referencedColumnName = "id")
    private Town town;

    //helper method
    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setAuthor(null);
    }

    protected Author() {
    }

    public Author(String firstName, String lastName, String nationality, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.birthYear = birthYear;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getNationality() { return nationality; }
    public int getBirthYear() { return birthYear; }
    public Set<Book> getBooks() { return books; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }
    public void setBooks(Set<Book> books) { this.books = books; }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
