package poc.tcc.sgm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.dtos.ApplicationUser;
import poc.tcc.sgm.dtos.UserOutDTO;
import poc.tcc.sgm.gateways.MuaAPIClient;

@Slf4j
@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private MuaAPIClient muaAPIClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserOutDTO userOutDTO;
		try {
			userOutDTO = this.muaAPIClient.findUserByUsername(username);
		} catch (Exception e) {
			log.error("Error finding user {}", username, e);
			throw new UsernameNotFoundException("User not found.");
		}
		log.info("User {} found", userOutDTO);
		return new ApplicationUser(userOutDTO);
	}

}
