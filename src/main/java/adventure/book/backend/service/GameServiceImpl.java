package adventure.book.backend.service;

import adventure.book.backend.dto.Book;
import adventure.book.backend.dto.Consequence;
import adventure.book.backend.dto.Option;
import adventure.book.backend.dto.Section;
import adventure.book.backend.enums.ConsequenceType;
import adventure.book.backend.enums.GameStatus;
import adventure.book.backend.enums.SectionType;
import adventure.book.backend.logic.Game;
import adventure.book.backend.repository.BookRepository;
import adventure.book.backend.repository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private static final int INITIAL_HEALTH = 10;

    private final BookRepository bookRepository;
    private final GameRepository gameRepository;


    public GameServiceImpl(BookRepository bookRepository, GameRepository gameRepository) {
        this.bookRepository = bookRepository;
        this.gameRepository = gameRepository;
    }


    @Override
    public Game startGame(String playerId, String title) {

        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() ->
                        new RuntimeException("Book not found: " + title)
                );

        Section beginSection = book.getSections()
                .stream()
                .filter(section -> section.getType() == SectionType.BEGIN)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Book does not have a BEGIN section"
                        )
                );

        Game game = new Game(
                playerId,
                book,
                beginSection.getId(),
                INITIAL_HEALTH,
                GameStatus.PLAYING
        );

        gameRepository.save(playerId, game);

        return game;
    }

    @Override
    public Game getCurrentGame(String playerId) {

        return gameRepository.findByPlayerId(playerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No active game for player: " + playerId
                        )
                );
    }

    @Override
    public Game chooseOption(
            String playerId,
            Integer gotoId
    ) {

        Game currentGame = gameRepository
                .findByPlayerId(playerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No active game for player: " + playerId
                        )
                );

        if (currentGame.getStatus() != GameStatus.PLAYING) {
            throw new RuntimeException("The game is already over");
        }

        Section currentSection = getCurrentSection(currentGame);

        Option selectedOption = currentSection.getOptions()
                .stream()
                .filter(currentOption ->
                        currentOption.getGotoId().equals(gotoId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid option: " + gotoId
                        )
                );

        applyConsequence(currentGame, selectedOption);

        if (currentGame.getHealth() <= 0) {

            currentGame.setHealth(0);
            currentGame.setStatus(GameStatus.DEAD);

            gameRepository.save(playerId, currentGame);

            return currentGame;
        }

        moveToSection(currentGame, selectedOption.getGotoId());

        gameRepository.save(playerId, currentGame);

        return currentGame;
    }


    private Section getCurrentSection(Game currentGame) {

        return currentGame.getBook()
                .getSections()
                .stream()
                .filter(section ->
                        section.getId().equals(
                                currentGame.getCurrentSectionId()
                        )
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Current section not found"
                        )
                );
    }

    private void applyConsequence(Game currentGame, Option option) {

        if (option.getConsequence() == null) {
            return;
        }

        Consequence consequence = option.getConsequence();

        if (consequence.getType() == ConsequenceType.LOSE_HEALTH) {

            int healthLost = consequence.getValue();

            currentGame.setHealth(
                    currentGame.getHealth() - healthLost
            );
        }
    }

    private void moveToSection(Game currentGame, Integer sectionId) {

        Section nextSection = currentGame.getBook()
                .getSections()
                .stream()
                .filter(section ->
                        section.getId().equals(sectionId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Section not found: " + sectionId
                        )
                );

        currentGame.setCurrentSectionId(
                nextSection.getId()
        );

        if (nextSection.getType() == SectionType.END) {
            currentGame.setStatus(GameStatus.FINISHED);
        }
    }

}
