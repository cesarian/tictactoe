package nl.cesar.tictactoe.data.transfer.model.response;

import java.util.List;

import nl.cesar.tictactoe.data.transfer.model.ResponseModel;
import nl.cesar.tictactoe.domain.Game;

public class GetOpenGamesResponseModel extends ResponseModel {
	
	private List<Game> openGames;

	public List<Game> getOpenGames() {
		return openGames;
	}

	public void setOpenGames(List<Game> openGames) {
		this.openGames = openGames;
	}

}
