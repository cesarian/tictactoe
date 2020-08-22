package nl.cesar.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import nl.cesar.tictactoe.data.transfer.model.GameDataTransferModel;
import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.service.GameService;
import nl.cesar.tictactoe.service.PlayerService;

@Controller
@RequestMapping(path = "/game")
public class GameController {
	private final GameService gameService;
	private final PlayerService playerService;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }
    
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Game createNewGame(@RequestBody GameDataTransferModel gameDataModel) {
        Game game = gameService.createNewGame(gameDataModel);
        
        return game;
    }

}
