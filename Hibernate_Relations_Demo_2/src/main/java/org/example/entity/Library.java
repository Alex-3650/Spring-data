package org.example.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "libraries")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "address",nullable = false,unique = true)
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "library_books",
               joinColumns = @JoinColumn(name = "library_id"),
               inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set <Book> books = new HashSet<Book>();


    public Library(String name, String address) {
        this.name = name;
        this.address = address;
    }

    protected Library() {
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getLibraries().add(this);
    }
    public void removeBook(Book book) {
        this.books.remove(book);
        book.getLibraries().remove(this);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
