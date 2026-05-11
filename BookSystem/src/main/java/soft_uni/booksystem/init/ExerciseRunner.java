package soft_uni.booksystem.init;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import soft_uni.booksystem.dto.AuthorBookCountView;
import soft_uni.booksystem.dto.AuthorFullNameView;
import soft_uni.booksystem.dto.BookTitleView;

import soft_uni.booksystem.dto.BooksByAuthorView;
import soft_uni.booksystem.service.AuthorService;
import soft_uni.booksystem.service.BookService;

import java.util.List;

@Component
@Order(2)
public class ExerciseRunner implements CommandLineRunner {

    private final BookService bookService;
    private final AuthorService authorService;

     @Autowired
    public ExerciseRunner(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
         this.authorService = authorService;
     }


    @Override
    public void run(String... args) throws Exception {

        List<BookTitleView> allBooksAfter2000 = bookService.findByReleaseDateAfter();
        System.out.println("Books released after 2000:");
        System.out.println();
        allBooksAfter2000.forEach(bookTitleView -> System.out.println(bookTitleView.getTitle()));

        List<AuthorFullNameView> allAuthorsWithBooksBefore1990 = authorService.findAllAuthorsWithBooksBefore1990();
        System.out.println("All Authors with books before 1990");
        allAuthorsWithBooksBefore1990.forEach(System.out::println);

        System.out.println("Print all authors descending by book count.");
        List<AuthorBookCountView> allAuthorsOrderByBooksCountDesc = authorService.findAllAuthorsOrderByBooksCountDesc();
        allAuthorsOrderByBooksCountDesc.forEach(System.out::println);

        System.out.println("All books by George Powell");
        List<BooksByAuthorView> georgePowellBooks = bookService.findByAuthor();
         georgePowellBooks.forEach(System.out::println);
    }
}
