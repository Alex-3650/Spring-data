package soft_uni.booksystem.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;
import soft_uni.booksystem.enums.AgeRestriction;
import soft_uni.booksystem.enums.EditionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_restriction",nullable = false,length = 50)
    private AgeRestriction ageRestriction;

    @Basic
    private int copies;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "edition_type",nullable = false,length = 50)
    private EditionType editionType;

    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "release_date",nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false,length = 255)
    private String title;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<Category>();


    public Book(AgeRestriction ageRestriction, int copies, String description, EditionType editionType, BigDecimal price, LocalDate releaseDate, String title, Author author, Set<Category> categories) {
        this.ageRestriction = ageRestriction;
        this.copies = copies;
        this.description = description;
        this.editionType = editionType;
        this.price = price;
        this.releaseDate = releaseDate;
        this.title = title;
        this.author = author;
        this.categories = categories;
    }

    public Book() {

    }

    //Helper method
    public void addAuthor(Author author) {
        this.author = author;
        author.getBooks().add(this);
    }



    public long getId() { return id; }

    public AgeRestriction getAgeRestriction() { return ageRestriction; }
    public void setAgeRestriction(AgeRestriction ageRestriction) { this.ageRestriction = ageRestriction; }

    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public EditionType getEditionType() { return editionType; }
    public void setEditionType(EditionType editionType) { this.editionType = editionType; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }


    @Override
    public String toString() {
        return "Title - %s  Released Date - %s".formatted(this.title,this.releaseDate);
    }
}
