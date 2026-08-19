package adventure.book.backend.repository;

import adventure.book.backend.dto.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findByTitle(String title);

    void save(Book book, String bookName);

}