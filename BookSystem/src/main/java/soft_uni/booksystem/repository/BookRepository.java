package soft_uni.booksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soft_uni.booksystem.dto.BookTitleView;
import soft_uni.booksystem.dto.BooksByAuthorView;
import soft_uni.booksystem.entity.Book;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {


//    List<Book> findAllByReleaseDateAfter(LocalDate of);

    List<BookTitleView> findAllTitlesByReleaseDateAfter(LocalDate of);



    @Query("SELECT new soft_uni.booksystem.dto.BooksByAuthorView(b.title, b.releaseDate, b.copies) " +
            "FROM Book b " +
            "WHERE b.author.firstName = :firstName AND b.author.lastName = :lastName " +
            "ORDER BY b.releaseDate DESC, b.title ASC")
    List<BooksByAuthorView> findAllByAuthorOrderByReleaseDateDescAndByTitleAsc(@Param("firstName") String firstName,
                                                                               @Param("lastName") String lastName);



}
