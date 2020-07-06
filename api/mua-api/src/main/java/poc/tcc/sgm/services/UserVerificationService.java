package poc.tcc.sgm.services;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.dtos.UserOutDTO;
import poc.tcc.sgm.exceptions.UserAlreadyExistsException;
import poc.tcc.sgm.exceptions.UserNotFoundException;
import poc.tcc.sgm.models.User;
import poc.tcc.sgm.repositories.UserRepository;

@Slf4j
@Service
public class UserVerificationService {

	private UserRepository userRepository;
	
	private MessageSource message;
	
	@Autowired
	public UserVerificationService(UserRepository userRepository, MessageSource message) {
		this.userRepository = userRepository;
		this.message = message;
	}
	
	public void checkUser(UserInDTO userInDTO) {
		this.checkExistsUserName(userInDTO);
		this.checkExistsEmail(userInDTO);
		this.checkExistsDocumentNumber(userInDTO);
		log.info("User {} is valid for registration", userInDTO.getUserName());
	}
	
	private void checkExistsUserName(UserInDTO userInDTO) {
		Optional<User> existingUserOptional = this.userRepository.findByUserName(userInDTO.getUserName());
		if (existingUserOptional.isPresent()) {
			final String message = this.message.getMessage("user.already.exists.username", 
					new Object[] { userInDTO.getUserName() }, Locale.getDefault());
			log.error("{}", message);
			throw new UserAlreadyExistsException(message);
		}
	}
	
	private void checkExistsEmail(UserInDTO userInDTO) {
		Optional<User> existingUserOptional = this.userRepository.findByEmail(userInDTO.getEmail());
		if (existingUserOptional.isPresent()) {
			final String message = this.message.getMessage("user.already.exists.email", 
					new Object[] { userInDTO.getEmail() }, Locale.getDefault());
			log.error("{}", message);
			throw new UserAlreadyExistsException(message);
		}
	}
	
	private void checkExistsDocumentNumber(UserInDTO userInDTO) {
		Optional<User> existingUserOptional = this.userRepository.findByDocumentNumber(userInDTO.getDocumentNumber());
		if (existingUserOptional.isPresent()) {
			final String message = this.message.getMessage("user.already.exists.documentnumber", 
					new Object[] { userInDTO.getDocumentNumber() }, Locale.getDefault());
			log.error("{}", message);
			throw new UserAlreadyExistsException(message);
		}
	}
	
	public UserOutDTO findByUserName(final String userName) {
		Optional<User> userOptional = this.userRepository.findByUserName(userName);
		if (!userOptional.isPresent()) {
			final String message = this.message.getMessage("user.not.found.username", 
					new Object[] { userName }, Locale.getDefault());
			log.error("{}", message);
			throw new UserNotFoundException(message);
		}
		return userOptional.get().convertToDto();
	}

}
