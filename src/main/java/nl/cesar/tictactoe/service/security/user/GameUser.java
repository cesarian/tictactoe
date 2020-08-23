package nl.cesar.tictactoe.service.security.user;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import nl.cesar.tictactoe.domain.Player;

public class GameUser extends User{
	
	private final Player player;
	
	public GameUser(Player player, Set<GrantedAuthority> authorities) {
		
		super(player.getUsername(),
				player.getPassword(),
                true,
                true,
                true,
                true,
                authorities);

        this.player = player;
	}

	public Long getUserId() {
		return player.getId();
	}
	
	public String getUsername() {
		return player.getUsername();
	}


}
