package nl.cesar.tictactoe.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game")
public class Game {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer player1Id;
	private Integer player2Id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPlayer1Id() {
		return player1Id;
	}
	public void setPlayer1Id(Integer player1Id) {
		this.player1Id = player1Id;
	}
	public Integer getPlayer2Id() {
		return player2Id;
	}
	public void setPlayer2Id(Integer player2Id) {
		this.player2Id = player2Id;
	}

}
