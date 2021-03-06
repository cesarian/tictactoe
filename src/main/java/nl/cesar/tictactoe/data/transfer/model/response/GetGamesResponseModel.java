package nl.cesar.tictactoe.data.transfer.model.response;

import java.util.ArrayList;
import java.util.List;

import nl.cesar.tictactoe.data.transfer.model.ResponseModel;
import nl.cesar.tictactoe.domain.Game;

public class GetGamesResponseModel extends ResponseModel {
	
	private List<Game> games = new ArrayList<Game>();

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

}
