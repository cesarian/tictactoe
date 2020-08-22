package nl.cesar.tictactoe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import nl.cesar.tictactoe.data.transfer.model.PlayerDataTranferModel;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.repository.PlayerRepository;

public class PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Player register(PlayerDataTranferModel playerDataModel) {
		Player player = new Player();
		player.setUsername(playerDataModel.getUsername());
		player.setPassword(passwordEncoder.encode(playerDataModel.getPassword()));
        
        playerRepository.save(player);
        
        return player;
	}

}
