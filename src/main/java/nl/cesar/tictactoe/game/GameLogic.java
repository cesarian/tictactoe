package nl.cesar.tictactoe.game;

import java.util.stream.IntStream;

import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.domain.GameData;

public class GameLogic {
	
	private int[][] winningPatterns = {{1,2,3}, {4,5,6}, {7,8,9}, {1,4,7}, {2,5,8}, {3,6,9}, {1,5,9}, {3,5,7}};
	
	private Game game;
	
	public GameLogic(Game game) {
		this.game = game;
	};
	
	private String getSymbol(int position) {
		if(game.getGameData() != null) {
			GameData data = game.getGameData();
			
			switch (position) {
				case 1 : return data.getP1();
				case 2 : return data.getP2();
				case 3 : return data.getP3();
				case 4 : return data.getP4();
				case 5 : return data.getP5();
				case 6 : return data.getP6();
				case 7 : return data.getP7();
				case 8 : return data.getP8();
				case 9 : return data.getP9();
				default : return null;
			}
		}
		
		return null;
	}
	
	private Boolean checkWinningPattern(int position, int[] pattern) {
		String symbol = getSymbol(position);
		
		if(symbol!= null && symbol.isEmpty()) {
			return false;
		}
		
		if(symbol != null && !symbol.isEmpty()) {
			int i;
			for (i = 0; i < pattern.length; i++) {
				if(pattern[i] != position && symbol != getSymbol(pattern[i])) {
					break;
				}
			}
			
			if(i == 2) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public Boolean checkWinningPatterns(int position) {
		int i = 0;
		
		for (i = 0; i < winningPatterns.length; i++) {
			boolean contains = IntStream.of(winningPatterns[i]).anyMatch(x -> x == position);
			
			if(contains) {
				if(checkWinningPattern(position, winningPatterns[i])) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
