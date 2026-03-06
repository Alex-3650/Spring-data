package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Library;
import org.example.entity.Town;

import java.util.function.ToDoubleBiFunction;

public class Main {
    public static void main(String[] args){
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("base");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Author author1 = new Author("Michael","Boris","Polish",2002);
        Author author2 = new Author("Leyla","Linkins","British",2001);

        Book book1 = new Book("title1","23A433",2020,"horror");
        Book book2 = new Book("title2","98B433",2022,"sci-fi");

        Library library1 = new Library("name1","str.Nowhere 24");
        Library library2 = new Library("name2","str.Sesame 24");

        Town barcelona = new Town("Barcelona","Spain");

        author1.setTown(barcelona);
        author2.setTown(barcelona);

        author1.addBook(book1);
        author2.addBook(book2);

        library1.addBook(book1);
        library1.addBook(book2);
        library2.addBook(book1);
        library2.addBook(book2);

        barcelona.addAuthor(author1);
        barcelona.addAuthor(author2);
        entityManager.getTransaction().commit();
    }
}