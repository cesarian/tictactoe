package nl.cesar.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.cesar.tictactoe.data.transfer.model.PlayerDataTranferModel;
import nl.cesar.tictactoe.data.transfer.model.PlayerLoginResponseDataModel;
import nl.cesar.tictactoe.data.transfer.model.PlayerRegisterResponseDataModel;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.service.CustomUserDetailsService;
import nl.cesar.tictactoe.service.PlayerService;
import nl.cesar.tictactoe.service.security.user.GameUser;

@Controller
@RequestMapping(path = "/player")
public class PlayerController {
	
	private PlayerService playerService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public PlayerRegisterResponseDataModel register(@RequestBody PlayerDataTranferModel playerDataModel) {
        Player player = playerService.register(playerDataModel);
        
        PlayerRegisterResponseDataModel reponse = new PlayerRegisterResponseDataModel();
        
        if(player.getId() != null) {
        	reponse.setRegisterSuccessful(true);
        	reponse.setRegisterMessage("Success");
        } else {
        	reponse.setRegisterSuccessful(false);
        	reponse.setRegisterMessage("Error");
        }
        
        
        return reponse;
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public PlayerLoginResponseDataModel login(@RequestBody PlayerDataTranferModel playerDataModel) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(playerDataModel.getUsername(), playerDataModel.getPassword())
		);
		
		final GameUser gameUser = (GameUser) customUserDetailsService
				.loadUserByUsername(playerDataModel.getUsername());
		PlayerLoginResponseDataModel response = new PlayerLoginResponseDataModel(gameUser.getUsername(), gameUser.getUserId());

        return response;
    }

}
