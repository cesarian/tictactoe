package nl.cesar.tictactoe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import nl.cesar.tictactoe.data.transfer.model.request.GameJoinRequestModel;
import nl.cesar.tictactoe.data.transfer.model.request.MoveRequestModel;
import nl.cesar.tictactoe.data.transfer.model.response.GameJoinResponseModel;
import nl.cesar.tictactoe.data.transfer.model.response.GetGamesResponseModel;
import nl.cesar.tictactoe.data.transfer.model.response.MoveResponseModel;
import nl.cesar.tictactoe.data.transfer.model.response.StartGameResponseModel;
import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.service.CustomUserDetailsService;
import nl.cesar.tictactoe.service.GameService;
import nl.cesar.tictactoe.service.PlayerService;
import nl.cesar.tictactoe.service.exception.GameException;
import nl.cesar.tictactoe.util.GameState;

@Controller
@RequestMapping(path = "/game")
public class GameController {
	
	private final GameService gameService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
    }
    
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity<?> createNewGame() {
    	HttpHeaders responseHeaders = new HttpHeaders();
		StartGameResponseModel response = new StartGameResponseModel();
		response.setActionSuccessful(false);
		
		String loggedInUser = customUserDetailsService.getLoggedInUser().getUsername();
    	
		try {
			Game game = gameService.createNewGame(loggedInUser);
			
			response.setGameId(game.getId());
			response.setGameState(game.getGameState());
			response.setPlayer1Symbol(game.getGameData().getPlayer1Symbol());
			response.setPlayer2Symbol(game.getGameData().getPlayer2Symbol());
			response.setActionSuccessful(true);
			response.setMessage("Success");
		} catch (Exception e) {
			response.setMessage("Failed to start a game. Please try again");
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<?> joinGame(@RequestBody GameJoinRequestModel gameJoinRequestModel) {
    	HttpHeaders responseHeaders = new HttpHeaders();
    	GameJoinResponseModel response = new GameJoinResponseModel();
    	response.setActionSuccessful(false);
    	
    	String loggedInUser = customUserDetailsService.getLoggedInUser().getUsername();
    	
		try {
			Game game = gameService.joinGame(loggedInUser, gameJoinRequestModel);
			
			response.setGameId(game.getId());
			response.setGameState(game.getGameState());
			response.setPlayer1Symbol(game.getGameData().getPlayer1Symbol());
			response.setPlayer2Symbol(game.getGameData().getPlayer2Symbol());
			response.setActionSuccessful(true);
			response.setMessage("Success");
		} catch(GameException e) {
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setMessage("Failed to join game ".concat(gameJoinRequestModel.getGameId().toString()).concat(" Please try again"));
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/opengames", method = RequestMethod.GET)
    public ResponseEntity<?> getOpenGames() {
    	HttpHeaders responseHeaders = new HttpHeaders();
		GetGamesResponseModel response = new GetGamesResponseModel();
		response.setActionSuccessful(false);
    	
		try {
			List<Game> games = gameService.getOpenGames();
			
			response.setGames(games);
			response.setActionSuccessful(true);
			response.setMessage("Success");
		} catch (Exception e) {
			response.setMessage("Failed to retrieve available games.");
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/allgames", method = RequestMethod.GET)
    public ResponseEntity<?> getAllGames() {
    	HttpHeaders responseHeaders = new HttpHeaders();
		GetGamesResponseModel response = new GetGamesResponseModel();
		response.setActionSuccessful(false);
    	
		try {
			List<Game> games = gameService.getAllGames();
			
			response.setGames(games);
			response.setActionSuccessful(true);
			response.setMessage("Success");
		} catch (Exception e) {
			response.setMessage("Failed to retrieve available games.");
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    public ResponseEntity<?> move(@RequestBody MoveRequestModel moveRequestModel) {
    	HttpHeaders responseHeaders = new HttpHeaders();
    	MoveResponseModel response = new MoveResponseModel();
    	response.setActionSuccessful(false);
    	
    	String loggedInUser = customUserDetailsService.getLoggedInUser().getUsername();
    	
		try {
			Game game = gameService.makeMove(loggedInUser, moveRequestModel);
			
			if(game.getGameState() == GameState.FINISHED) {
				response.setWinnerId(game.getWinnerId());
			}
			
			response.setGameState(game.getGameState());
			response.setActionSuccessful(true);
			response.setMessage("Success");
		} catch(GameException e) {
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed to make a move. Please try again");
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

}
