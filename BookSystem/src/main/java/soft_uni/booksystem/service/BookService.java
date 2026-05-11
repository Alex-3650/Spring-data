package soft_uni.booksystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import soft_uni.booksystem.dto.BookTitleView;
import soft_uni.booksystem.dto.BooksByAuthorView;
import soft_uni.booksystem.entity.Book;
import soft_uni.booksystem.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }


    public List<BookTitleView> findByReleaseDateAfter() {
        return bookRepository.findAllTitlesByReleaseDateAfter(LocalDate.of(2000,12,31));
    }

    public List<BooksByAuthorView> findByAuthor() {
       return bookRepository.findAllByAuthorOrderByReleaseDateDescAndByTitleAsc("George", "Powell");
    }


}
