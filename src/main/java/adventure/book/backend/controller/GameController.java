package adventure.book.backend.controller;

import adventure.book.backend.logic.Game;
import adventure.book.backend.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/{playerId}/start/{title}")
    public Game startGame(
            @PathVariable String playerId,
            @PathVariable String title
    ) {
        return gameService.startGame(playerId, title);
    }

    @GetMapping("/{playerId}")
    public Game getCurrentGame(
            @PathVariable String playerId
    ) {
        return gameService.getCurrentGame(playerId);
    }

    @PostMapping("/{playerId}/choose/{gotoId}")
    public Game chooseOption(
            @PathVariable String playerId,
            @PathVariable Integer gotoId
    ) {
        return gameService.chooseOption(
                playerId,
                gotoId
        );
    }
}