package poc.tcc.sgm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.dtos.UserOutDTO;
import poc.tcc.sgm.gateways.SafimAPIClient;
import poc.tcc.sgm.repositories.UserRepository;

@Slf4j
@Service
public class UserService {

	private UserRepository userRepository;
	
	private SafimAPIClient safimAPIClient;
	
	@Autowired
	public UserService(UserRepository userRepository, SafimAPIClient safimAPIClient) {
		this.userRepository = userRepository;
		this.safimAPIClient = safimAPIClient;
	}
	
	public UserOutDTO createUser(UserInDTO userInDTO) {
		return null;
	}
	
	public UserOutDTO findByUserName(final String userName) {
		return null;
	}
	
}
