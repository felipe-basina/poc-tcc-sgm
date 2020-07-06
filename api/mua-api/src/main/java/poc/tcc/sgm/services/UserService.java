package poc.tcc.sgm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.dtos.UserOutDTO;
import poc.tcc.sgm.models.User;
import poc.tcc.sgm.models.UserProfile;
import poc.tcc.sgm.repositories.UserRepository;

@Slf4j
@Service
public class UserService {

	private UserRepository userRepository;
	
	private UserVerificationService userVerificationService;
	
	private UserProfileService userProfileService;
	
	@Autowired
	public UserService(UserRepository userRepository, UserVerificationService userVerificationService,
			UserProfileService userProfileService) {
		this.userRepository = userRepository;
		this.userVerificationService = userVerificationService;
		this.userProfileService = userProfileService;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UserOutDTO createUser(UserInDTO userInDTO) {
		this.userVerificationService.checkUser(userInDTO);
		UserProfile userProfile = this.userProfileService.getUserProfile(userInDTO);
		User newUser = userInDTO.convertToNewEntity(userProfile);
		newUser = this.userRepository.save(newUser);
		log.info("User {} | {} created!", newUser.getId(), newUser.getUserName());
		return newUser.convertToDto();
	}
	
	public UserOutDTO findByUserName(final String userName) {
		return this.userVerificationService.findByUserName(userName);
	}
	
}
