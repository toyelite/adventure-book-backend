package adventure.book.backend.service;

import adventure.book.backend.dto.Book;
import adventure.book.backend.enums.Difficulty;
import adventure.book.backend.request.CreateBookRequest;

import java.util.List;

public interface AdventureBookService {

    List<Book> search(String title, String author, Difficulty difficulty);

    void addCategory(String title, String category);

    void removeCategory(String title, String category);

    Book createBook(CreateBookRequest request);

}