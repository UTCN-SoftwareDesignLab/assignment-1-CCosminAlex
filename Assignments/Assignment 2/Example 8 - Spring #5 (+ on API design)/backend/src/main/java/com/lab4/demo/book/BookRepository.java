package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBookByGenre(String genre);

    List<Book> findBookByTitle(String title);

    List<Book> findBookByAuthor(String author);

    List<Book> findBookByGenreOrTitleOrAuthor(String genre, String title, String author);

    List<Book> findBookByQuantity(int quantity);

}
