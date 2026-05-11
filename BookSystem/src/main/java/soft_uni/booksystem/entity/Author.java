package soft_uni.booksystem.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false,length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false,length = 255)
    private String lastName;

    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<Book>();


    //Helper methods
    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setAuthor(null);
    }

    public Author() {

    }


    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public long getId() {return id;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public Set<Book> getBooks() {return books;}
}
