package nl.cesar.tictactoe.util;

import org.springframework.stereotype.Component;

import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.domain.GameData;

@Component
public class MoveUtil {
	
	public Character getSymbolInPosition(Game game, int position) {
		if(game.getGameData() != null) {
			GameData data = game.getGameData();
			
			switch (position) {
				case 1: 
					return data.getP1();
				case 2: 
					return data.getP2();
				case 3: 
					return data.getP3();
				case 4: 
					return data.getP4();
				case 5: 
					return data.getP5();
				case 6: 
					return data.getP6();
				case 7: 
					return data.getP7();
				case 8: 
					return data.getP8();
				case 9: 
					return data.getP9();
				default: 
					return null;
			}
		}
		
		return null;
	}
	
	public void setSymbolInPosition(Game game, int position, Character symbol) {
		System.out.println("setSymbolInPosition " + game.getId() + "-"+ position + "-" + symbol.toString());
		if(game.getGameData() != null) {
			GameData data = game.getGameData();
			
			switch (position) {
				case 1: 
					data.setP1(symbol);
					break;
				case 2: 
					data.setP2(symbol);
					break;
				case 3: 
					data.setP3(symbol);
					break;
				case 4: 
					data.setP4(symbol);
					break;
				case 5: 
					data.setP5(symbol);
					break;
				case 6: 
					data.setP6(symbol);
					break;
				case 7: 
					data.setP7(symbol);
					break;
				case 8: 
					data.setP8(symbol);
					break;
				case 9: 
					data.setP9(symbol);
					break;
				default: 
					break;
			}
		}
	}
}
