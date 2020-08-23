package nl.cesar.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.cesar.tictactoe.data.transfer.model.request.AuthenticationRequestModel;
import nl.cesar.tictactoe.data.transfer.model.response.LoginResponseModel;
import nl.cesar.tictactoe.data.transfer.model.response.RegisterResponseModel;
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
    public ResponseEntity<?> register(@RequestBody AuthenticationRequestModel playerDataModel) {
		HttpHeaders responseHeaders = new HttpHeaders();
		RegisterResponseModel response = new RegisterResponseModel();
		
		try {
			Player player = playerService.register(playerDataModel);
			
			response.setUserId(player.getId());
			response.setActionSuccessful(true);
	        response.setMessage("Success");
	        
	        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
		}
		catch(Exception e) {
			response.setActionSuccessful(false);
        	response.setMessage("Something went wrong. Please try again or use another username.");
			
			return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
		}
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?>  login(@RequestBody AuthenticationRequestModel playerDataModel) {
		HttpHeaders responseHeaders = new HttpHeaders();
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(playerDataModel.getUsername(), playerDataModel.getPassword())
		);
		
		LoginResponseModel response = new LoginResponseModel();
		
		try {
			
			final GameUser gameUser = (GameUser) customUserDetailsService
					.loadUserByUsername(playerDataModel.getUsername());
			
			response.setUserId(gameUser.getUserId());
			response.setUsername(gameUser.getUsername());
			response.setActionSuccessful(true);
			response.setMessage("Success");
			
		} catch (UsernameNotFoundException e) {
			response.setActionSuccessful(false);
			response.setMessage("Login failed.".concat(e.getMessage()));
		} catch (Exception e) {
			response.setActionSuccessful(false);
			response.setMessage("Login failed. Please try again");
		}

		return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

}
