package adventure.book.backend.service;

import adventure.book.backend.logic.Game;

public interface GameService {

    Game startGame(String playerId, String title);

    Game getCurrentGame(String playerId);

    Game chooseOption(String playerId, Integer gotoId);

}