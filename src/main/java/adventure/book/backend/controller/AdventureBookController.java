package adventure.book.backend.controller;

import adventure.book.backend.dto.Book;
import adventure.book.backend.enums.Difficulty;
import adventure.book.backend.request.CreateBookRequest;
import adventure.book.backend.service.AdventureBookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adventure/book")
public class AdventureBookController {

    private final AdventureBookService adventureBookService;

    public AdventureBookController(AdventureBookService adventureBookService) {
        this.adventureBookService = adventureBookService;
    }


    @GetMapping
    public List<Book> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Difficulty difficulty
    ) {
        return adventureBookService.search(title, author, difficulty);
    }


    @PostMapping("/categories")
    public void addCategory(
            @RequestParam String title,
            @RequestParam String category
    ) {
        adventureBookService.addCategory(
                title,
                category
        );
    }

    @DeleteMapping("/categories")
    public void removeCategory(
            @RequestParam String title,
            @RequestParam String category
    ) {
        adventureBookService.removeCategory(title, category);
    }

    @PostMapping
    public Book createBook(
            @Valid @RequestBody CreateBookRequest request
    ) {
        return adventureBookService.createBook(request);
    }

}