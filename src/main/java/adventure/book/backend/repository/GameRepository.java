package adventure.book.backend.repository;

import adventure.book.backend.logic.Game;

import java.util.Optional;

public interface GameRepository {

    Optional<Game> findByPlayerId(String playerId);

    void save(String playerId, Game game);

}
