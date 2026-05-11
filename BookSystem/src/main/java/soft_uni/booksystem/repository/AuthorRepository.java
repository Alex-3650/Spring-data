package soft_uni.booksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soft_uni.booksystem.dto.AuthorBookCountView;
import soft_uni.booksystem.dto.AuthorFullNameView;
import soft_uni.booksystem.entity.Author;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author getAuthorById(long id);


    @Query("SELECT DISTINCT new soft_uni.booksystem.dto.AuthorFullNameView(a.firstName, a.lastName) " +
            "FROM Author a JOIN  a.books b WHERE b.releaseDate < :date")
    List<AuthorFullNameView> findAuthorsWithBooksBefore(@Param("date") LocalDate date);

    @Query("SELECT new soft_uni.booksystem.dto.AuthorBookCountView(a.firstName, a.lastName, COUNT(b)) " +
            "FROM Author a LEFT JOIN  a.books b " +
            "GROUP BY a.firstName, a.lastName " +
            "ORDER BY COUNT(b) DESC")
    List<AuthorBookCountView> findAllAuthorsOrderByBooksCountDesc();


}
