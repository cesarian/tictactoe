package nl.cesar.tictactoe.service;

import nl.cesar.tictactoe.data.transfer.model.GameDataTransferModel;
import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.repository.GameRepository;

public class GameService {
	
	private final GameRepository gameRepository;
	
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public Game createNewGame(GameDataTransferModel gameDataModel) {
		return null;
	}

}
