package nl.cesar.tictactoe.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.repository.PlayerRepository;
import nl.cesar.tictactoe.service.security.user.GameUser;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Player> player = playerRepository.findByUsername(userName);
		player.orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		Set<GrantedAuthority> unmodifiableSet = Collections.unmodifiableSet(authorities);
		
		return new GameUser(player.get(), unmodifiableSet);
	}
	
	public UserDetails getLoggedInUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
	}

}
