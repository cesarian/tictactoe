package nl.cesar.tictactoe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class GameData {
	
	private Character player1Symbol;
	private Character player2Symbol;
	
	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private String p5;
	private String p6;
	private String p7;
	private String p8;
	private String p9;
	
	public GameData() {
		player1Symbol = 'x';
		player2Symbol = 'o';
	}
	
	public Character getPlayer1Symbol() {
		return player1Symbol;
	}

	public void setPlayer1Symbol(Character player1Symbol) {
		this.player1Symbol = player1Symbol;
	}

	public Character getPlayer2Symbol() {
		return player2Symbol;
	}

	public void setPlayer2Symbol(Character player2Symbol) {
		this.player2Symbol = player2Symbol;
	}

	public String getP1() {
		return p1;
	}
	
	public void setP1(String p1) {
		this.p1 = p1;
	}
	
	public String getP2() {
		return p2;
	}
	
	public void setP2(String p2) {
		this.p2 = p2;
	}
	
	public String getP3() {
		return p3;
	}
	
	public void setP3(String p3) {
		this.p3 = p3;
	}
	
	public String getP4() {
		return p4;
	}
	
	public void setP4(String p4) {
		this.p4 = p4;
	}
	
	public String getP5() {
		return p5;
	}
	
	public void setP5(String p5) {
		this.p5 = p5;
	}
	
	public String getP6() {
		return p6;
	}
	
	public void setP6(String p6) {
		this.p6 = p6;
	}
	
	public String getP7() {
		return p7;
	}
	
	public void setP7(String p7) {
		this.p7 = p7;
	}
	
	public String getP8() {
		return p8;
	}
	
	public void setP8(String p8) {
		this.p8 = p8;
	}
	
	public String getP9() {
		return p9;
	}
	
	public void setP9(String p9) {
		this.p9 = p9;
	}

}
