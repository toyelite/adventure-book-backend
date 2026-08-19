package adventure.book.backend.repository;

import adventure.book.backend.dto.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final ObjectMapper objectMapper;
    private final Path booksDirectory;

    public BookRepositoryImpl(
            ObjectMapper objectMapper,
            @Value("${books.directory}") String booksDirectory
    ) {
        this.objectMapper = objectMapper;
        this.booksDirectory = Paths.get(booksDirectory);
    }

    @Override
    public List<Book> findAll() {

        try (Stream<Path> files = Files.list(booksDirectory)) {

            return files
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(this::readBook)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read books from " + booksDirectory,
                    e
            );
        }
    }

    @Override
    public Optional<Book> findByTitle(String title) {

        Path path = getBookPath(title);

        if (!Files.exists(path)) {
            return Optional.empty();
        }

        return Optional.of(readBook(path));
    }

    @Override
    public void save(Book book, String bookName) {

        Path path = getBookPath(bookName);

        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(path.toFile(), book);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to save book: " + bookName,
                    e
            );
        }
    }

    private Book readBook(Path path) {

        try {
            return objectMapper.readValue(
                    path.toFile(),
                    Book.class
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read book: " + path,
                    e
            );
        }
    }

    private Path getBookPath(String title) {

        return booksDirectory.resolve(
                title + ".json"
        );
    }
}