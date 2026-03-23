package soft_uni.booksystem.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import soft_uni.booksystem.entity.Author;
import soft_uni.booksystem.entity.Book;
import soft_uni.booksystem.entity.Category;
import soft_uni.booksystem.enums.AgeRestriction;
import soft_uni.booksystem.enums.EditionType;
import soft_uni.booksystem.repository.AuthorRepository;
import soft_uni.booksystem.repository.BookRepository;
import soft_uni.booksystem.repository.CategoryRepository;
import soft_uni.booksystem.service.AuthorService;
import soft_uni.booksystem.service.BookService;
import soft_uni.booksystem.service.CategoryService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(1)
public class DataSeeder implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;


   @Autowired
    public DataSeeder(CategoryRepository categoryRepository, AuthorRepository authorRepository, BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
       this.authorService = authorService;
       this.categoryService = categoryService;
   }

    private void seedCategories() throws IOException {
        ClassPathResource categoryFile = new ClassPathResource("SpringDataResources/files/categories.txt");

        List <String> lines = Files.readAllLines(Path.of(categoryFile.getURI()));

        for (String line : lines) {
            if (line.isBlank()) continue;

            Category category = new Category(line.trim());
            categoryRepository.save(category);

        }
    }
    private void seedAuthors() throws IOException {
    ClassPathResource authorFile = new ClassPathResource("SpringDataResources/files/authors.txt");
    List <String> lines = Files.readAllLines(Path.of(authorFile.getURI()));

    for (String line : lines) {
        if (line.isBlank()) continue;
        String[] authors = line.trim().split("\\s+");
        String firstName = authors[0];
        String lastName = authors[1];
        Author author = new Author(firstName, lastName);
        authorRepository.save(author);

    }
    }
    private void seedBooks() throws IOException {
        ClassPathResource authorFile = new ClassPathResource("SpringDataResources/files/books.txt");
        List <String> lines = Files.readAllLines(Path.of(authorFile.getURI()));

        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] books = line.trim().split("\\s+");
            EditionType editionType = EditionType.values()[Integer.parseInt(books[0])];
            LocalDate releaseDate = LocalDate.parse(books[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            int copies = Integer.parseInt(books[2]);
            BigDecimal price = new BigDecimal(books[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(books[4])];
            String title = Arrays.stream(books).skip(5) .collect(Collectors.joining(" "));
            Set<Category> categories = new HashSet<>(categoryService.getRandomCategory());
             Author author = authorService.getRandomAuthor();
             Book book = new Book(ageRestriction,  copies,  null,  editionType,  price,  releaseDate,  title,  author,  categories);

             bookRepository.save(book);



        }
    }

    @Override
    public void run(String... args) throws Exception {
        seedCategories();
        seedAuthors();
        seedBooks();

    }
}
