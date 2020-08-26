package nl.cesar.tictactoe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import nl.cesar.tictactoe.data.transfer.model.ResponseModel;
import nl.cesar.tictactoe.data.transfer.model.request.GameRequestModel;
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
    public ResponseEntity<?> startNewGame() {
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
		} catch (UsernameNotFoundException e) {
			response.setMessage(e.getMessage());
		} catch (GameException e) {
			response.setMessage(e.getMessage());
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<?> joinGame(@RequestBody GameRequestModel gameRequestModel) {
    	HttpHeaders responseHeaders = new HttpHeaders();
    	GameJoinResponseModel response = new GameJoinResponseModel();
    	response.setActionSuccessful(false);
    	
    	String loggedInUser = customUserDetailsService.getLoggedInUser().getUsername();
    	
		try {
			Game game = gameService.joinGame(loggedInUser, gameRequestModel.getGameId());
			
			response.setGameId(game.getId());
			response.setGameState(game.getGameState());
			response.setPlayer1Symbol(game.getGameData().getPlayer1Symbol());
			response.setPlayer2Symbol(game.getGameData().getPlayer2Symbol());
			response.setActionSuccessful(true);
			response.setMessage("Success");
		} catch (UsernameNotFoundException e) {
			response.setMessage(e.getMessage());
		} catch (GameException e) {
			response.setMessage(e.getMessage());
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public ResponseEntity<?> leaveGame(@RequestBody GameRequestModel gameRequestModel) {
    	HttpHeaders responseHeaders = new HttpHeaders();
    	ResponseModel response = new ResponseModel();
    	response.setActionSuccessful(false);
    	
    	String loggedInUser = customUserDetailsService.getLoggedInUser().getUsername();
    	
		try {
			gameService.leaveGame(loggedInUser, gameRequestModel.getGameId());
			
			response.setActionSuccessful(true);
			response.setMessage("Success");
		} catch (UsernameNotFoundException e) {
			response.setMessage(e.getMessage());
		} catch (GameException e) {
			response.setMessage(e.getMessage());
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/opengames", method = RequestMethod.GET)
    public ResponseEntity<?> getOpenGames() {
    	HttpHeaders responseHeaders = new HttpHeaders();
		GetGamesResponseModel response = new GetGamesResponseModel();
		response.setActionSuccessful(false);
		
		List<Game> games = gameService.getOpenGames();
    	
		if(games != null && !games.isEmpty()) {
			response.setGames(games);
		}
		
		response.setActionSuccessful(true);
		response.setMessage("Success");
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/allgames", method = RequestMethod.GET)
    public ResponseEntity<?> getAllGames() {
    	HttpHeaders responseHeaders = new HttpHeaders();
		GetGamesResponseModel response = new GetGamesResponseModel();
		response.setActionSuccessful(false);
		
		List<Game> games = gameService.getAllGames();
    	
		if(games != null && !games.isEmpty()) {
			response.setGames(games);
		}
		
		response.setActionSuccessful(true);
		response.setMessage("Success");
        
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
		} catch (UsernameNotFoundException e) {
			response.setMessage(e.getMessage());
		} catch (GameException e) {
			response.setMessage(e.getMessage());
		}
        
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

}
