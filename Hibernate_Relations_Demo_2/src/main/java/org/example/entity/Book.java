package org.example.entity;

import jakarta.persistence.*;

import javax.print.attribute.standard.MediaSize;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn",nullable = false,unique = true)
    private String isbn;

    @Column(name = "year",nullable = false)
    private int year;

    @Column(name = "genre",nullable = false)
    private String genre;

    @ManyToMany(mappedBy = "books")
    private Set <Library> libraries = new HashSet<Library>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    private Author author;


    public Book(String title, String isbn, int year, String genre) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.genre = genre;
    }

    protected Book() {
    }

    public void add(Library library) {
        this.libraries.add(library);
        library.getBooks().add(this);
    }

    public void remove(Library library) {
        this.libraries.remove(library);
        library.getBooks().remove(this);
    }


    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public Set<Library> getLibraries() {
        return libraries;
    }
    public void setLibraries(Set<Library> libraries) {
        this.libraries = libraries;
    }
    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
}
