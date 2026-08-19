package adventure.book.backend.service;

import adventure.book.backend.dto.Book;
import adventure.book.backend.enums.Difficulty;
import adventure.book.backend.repository.BookRepository;
import adventure.book.backend.request.CreateBookRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdventureBookServiceImpl implements AdventureBookService {

    private final BookRepository bookRepository;

    public AdventureBookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> search(
            String title,
            String author,
            Difficulty difficulty
    ) {
        String normalizedTitle = title == null
                ? null
                : title.toLowerCase();

        String normalizedAuthor = author == null
                ? null
                : author.toLowerCase();

        return bookRepository.findAll()
                .stream()
                .filter(book ->
                        normalizedTitle == null ||
                                normalizedTitle.isBlank() ||
                                book.getTitle()
                                        .toLowerCase()
                                        .contains(normalizedTitle)
                )
                .filter(book ->
                        normalizedAuthor == null ||
                                normalizedAuthor.isBlank() ||
                                book.getAuthor()
                                        .toLowerCase()
                                        .contains(normalizedAuthor)
                )
                .filter(book ->
                        difficulty == null ||
                                book.getDifficulty() == difficulty
                )
                .toList();
    }

    @Override
    public void addCategory(String title, String category) {

        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() ->
                        new RuntimeException("Book not found: " + title)
                );

        if (!book.getCategories().contains(category)) {
            book.getCategories().add(category);
        }

        bookRepository.save(book, title);
    }

    @Override
    public void removeCategory(String title, String category) {

        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() ->
                        new RuntimeException("Book not found: " + title)
                );

        book.getCategories().remove(category);

        bookRepository.save(book, title);
    }

    @Override
    public Book createBook(CreateBookRequest request) {

        if (bookRepository.findByTitle(request.title()).isPresent()) {
            throw new RuntimeException(
                    "A book with the title already exists: " + request.title()
            );
        }

        Book book = new Book();

        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setDifficulty(request.difficulty());
        book.setCategories(request.categories());
        book.setSections(request.sections());

        bookRepository.save(book, book.getTitle().toLowerCase().replace(" ", "-"));

        return book;
    }

}
