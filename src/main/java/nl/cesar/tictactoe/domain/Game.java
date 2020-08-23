package nl.cesar.tictactoe.domain;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.cesar.tictactoe.util.GameState;
import nl.cesar.tictactoe.util.GameType;
import nl.cesar.tictactoe.util.GameDataConverter;

@Entity
@Table(name = "game")
public class Game {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long player1Id;
	private Long player2Id;
	@Enumerated(EnumType.STRING)
	private GameType gameType;
	@Enumerated(EnumType.STRING)
	private GameState gameState;
	private Long winnerId;
	private Long playerTurn;
	@Convert(converter = GameDataConverter.class)
	private GameData gameData;
	
	public Game() {
		
	}
	
	public Game(Long player1Id) {
		this.player1Id = player1Id;
		this.gameState = GameState.OPEN;
		this.gameData = new GameData();
		this.playerTurn = player1Id;
	}

	public GameType getType() {
		return gameType;
	}
	
	public void setType(GameType type) {
		this.gameType = type;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public Long getWinnerId() {
		return winnerId;
	}
	
	public void setWinnerId(Long winnerId) {
		this.winnerId = winnerId;
	}
	
	public Long getId() {
		return id;
	}

	public Long getPlayer1Id() {
		return player1Id;
	}
	
	public void setPlayer1Id(Long player1Id) {
		this.player1Id = player1Id;
	}
	
	public Long getPlayer2Id() {
		return player2Id;
	}
	
	public void setPlayer2Id(Long player2Id) {
		this.player2Id = player2Id;
	}

	public GameData getGameData() {
		return gameData;
	}

	public void setGameData(GameData gameData) {
		this.gameData = gameData;
	}

	public Long getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(Long playerTurn) {
		this.playerTurn = playerTurn;
	}

}
