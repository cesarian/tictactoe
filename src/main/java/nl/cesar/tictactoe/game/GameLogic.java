package nl.cesar.tictactoe.game;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.cesar.tictactoe.data.transfer.model.request.MoveRequestModel;
import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.util.GameState;
import nl.cesar.tictactoe.util.MoveUtil;

@Component
public class GameLogic {
	
	private int[][] winningPatterns = {{1,2,3}, {4,5,6}, {7,8,9}, {1,4,7}, {2,5,8}, {3,6,9}, {1,5,9}, {3,5,7}};
	
	@Autowired
	private MoveUtil moveUtil;
	
	private Boolean checkWinningPattern(Game game, int position, int[] pattern) {
		Character symbol = moveUtil.getSymbolInPosition(game, position);
		
		if(symbol != null) {
			int i;
			for (i = 0; i < pattern.length; i++) {
				Character symbolInPosition = moveUtil.getSymbolInPosition(game, pattern[i]);
				if(pattern[i] != position && symbol != symbolInPosition) {
					break;
				}
			}
			
			if(i == 3) {
				return true;
			}
		}
		
		return false;
	}
	
	public Boolean checkWinningPatterns(Game game, int position) {
		int i;
		for (i = 0; i < winningPatterns.length; i++) {
			
			boolean contains = IntStream.of(winningPatterns[i]).anyMatch(x -> x == position);
			
			if(contains) {
				if(checkWinningPattern(game, position, winningPatterns[i])) {
					return true;
				}
			}
		}
		
		return false;
	}

	public Boolean checkDraw(Game game) {
		int i;
		int has2DifferentFilledPositonsCount = 0;
		for (i = 0; i < winningPatterns.length; i++) {
			int[] positions = winningPatterns[i];
			
			int s;
			Character firstSymbolFilled = null;
			for(s = 0; s < positions.length; s++) {
				Character c = moveUtil.getSymbolInPosition(game, positions[s]);
				
				if(c != null && firstSymbolFilled != null && firstSymbolFilled != c) {
					has2DifferentFilledPositonsCount++;
					break;
				}
				
				if(c != null && firstSymbolFilled == null) {
					firstSymbolFilled = c;
				}
			}
		}
		
		if(has2DifferentFilledPositonsCount == winningPatterns.length) {
			return true;
		}
		
		return false;
	}
	
	public void updateGameStateAfterMove(Game game, MoveRequestModel moveRequestModel, Player loggedInPlayer) {
		if(checkWinningPatterns(game, moveRequestModel.getPosition())) {
			game.setGameState(GameState.FINISHED);
			game.setWinnerId(loggedInPlayer.getId());
		} else if(checkDraw(game)) {
			game.setGameState(GameState.DRAW);
		} else {
			if(loggedInPlayer.getId() == game.getPlayer1Id()) {
				game.setPlayerTurn(game.getPlayer2Id());
			} else {
				game.setPlayerTurn(game.getPlayer1Id());
			}
		}
	}
	
	public void updateGameStateAfterPlayerLeave(Game game, Player loggedInPlayer) {
		game.setGameState(GameState.FINISHED);
		
		if(loggedInPlayer.getId() ==  game.getPlayer1Id() && game.getPlayer2Id() != null) {
			game.setWinnerId(game.getPlayer2Id());
		} if(loggedInPlayer.getId() ==  game.getPlayer1Id() && game.getPlayer2Id() == null) {
			game.setGameState(GameState.EXPIRED);
		} else {
			game.setWinnerId(game.getPlayer1Id());
		}
	}
	
}
