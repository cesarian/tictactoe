package nl.cesar.tictactoe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.cesar.tictactoe.data.transfer.model.request.AuthenticationRequestModel;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.repository.PlayerRepository;

@Service
@Transactional
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Player register(AuthenticationRequestModel authenticationRequestModel) throws Exception {
		Player player = new Player(authenticationRequestModel.getUsername(), passwordEncoder.encode(authenticationRequestModel.getPassword()));
		
		try {
			playerRepository.save(player);
		} catch(Exception e) {
			throw e;
		}
        
        return player;
	}
}
