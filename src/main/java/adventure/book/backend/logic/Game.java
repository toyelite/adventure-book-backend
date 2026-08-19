package adventure.book.backend.logic;

import adventure.book.backend.dto.Book;
import adventure.book.backend.enums.GameStatus;

public class Game {

    private String playerId;
    private Integer currentSectionId;
    private int health;
    private GameStatus status;
    private Book book;

    public Game(
            String playerId,
            Book book,
            Integer currentSectionId,
            int health,
            GameStatus status
    ) {
        this.playerId = playerId;
        this.book = book;
        this.currentSectionId = currentSectionId;
        this.health = health;
        this.status = status;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public Integer getCurrentSectionId() {
        return currentSectionId;
    }

    public void setCurrentSectionId(Integer currentSectionId) {
        this.currentSectionId = currentSectionId;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}