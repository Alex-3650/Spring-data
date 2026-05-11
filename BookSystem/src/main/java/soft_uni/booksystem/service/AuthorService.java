package soft_uni.booksystem.service;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.booksystem.dto.AuthorBookCountView;
import soft_uni.booksystem.dto.AuthorFullNameView;
import soft_uni.booksystem.entity.Author;
import soft_uni.booksystem.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class AuthorService {
   private final AuthorRepository  authorRepository;
   private final Random random = new Random();

   @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //Hard code count of authors.It is for seeding purposes only
    public Author getRandomAuthor() {
        int id = random.nextInt(1, 27);
       return authorRepository.getAuthorById(id);
    }

   public List<AuthorFullNameView> findAllAuthorsWithBooksBefore1990(){
      return authorRepository.findAuthorsWithBooksBefore(LocalDate.of(1990,1,1));
    }

    public List<AuthorBookCountView> findAllAuthorsOrderByBooksCountDesc() {
     return  authorRepository.findAllAuthorsOrderByBooksCountDesc();
    }
}
