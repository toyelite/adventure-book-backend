package adventure.book.backend.repository;

import adventure.book.backend.logic.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private final ObjectMapper objectMapper;
    private final Path gamesDirectory;

    public GameRepositoryImpl(
            ObjectMapper objectMapper,
            @Value("${games.directory}") String gamesDirectory
    ) {
        this.objectMapper = objectMapper;
        this.gamesDirectory = Paths.get(gamesDirectory);
    }

    @Override
    public Optional<Game> findByPlayerId(String playerId) {

        Path path = getGamePath(playerId);

        if (!Files.exists(path)) {
            return Optional.empty();
        }

        try {
            return Optional.of(
                    objectMapper.readValue(
                            path.toFile(),
                            Game.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read game for player: " + playerId,
                    e
            );
        }
    }

    @Override
    public void save(String playerId, Game game) {

        try {
            Files.createDirectories(gamesDirectory);

            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(
                            getGamePath(playerId).toFile(),
                            game
                    );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to save game for player: " + playerId,
                    e
            );
        }
    }


    private Path getGamePath(String playerId) {
        return gamesDirectory.resolve(playerId + ".json");
    }
}