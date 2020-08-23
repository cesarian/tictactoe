package nl.cesar.tictactoe.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.cesar.tictactoe.util.GameState;

@Entity
@Table(name = "game")
public class Game {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long player1Id;
	private Long player2Id;
	private String type;
	private GameState state;
	private String winner;
	private Long player1Score;
	private Long player2Score;
	@Embedded
	private GameData gameData;
	
	public Game(Long player1Id) {
		this.player1Id = player1Id;
		this.state = GameState.OPEN;
		this.gameData = new GameData();
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public GameState getState() {
		return state;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
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
	
	public Long getPlayer1Score() {
		return player1Score;
	}

	public void setPlayer1Score(Long player1Score) {
		this.player1Score = player1Score;
	}

	public Long getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(Long player2Score) {
		this.player2Score = player2Score;
	}

	public GameData getGameData() {
		return gameData;
	}

	public void setGameData(GameData gameData) {
		this.gameData = gameData;
	}

}
