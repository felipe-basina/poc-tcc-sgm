package poc.tcc.sgm.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.dtos.UserOutDTO;
import poc.tcc.sgm.services.UserService;

@Slf4j
@RestController
@RequestMapping(value = "${poc.tcc.sgm.api.user.api}")
@SuppressWarnings("rawtypes")
public class UserAPI {

	private UserService userService;
	
	@Autowired
	public UserAPI(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createUser(@Valid @RequestBody UserInDTO userInDTO) {
		try {
			UserOutDTO userOutDTO = this.userService.createUser(userInDTO);
			log.info("New user created: {}", userOutDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(userOutDTO);
		} catch (Exception e) {
			log.error("Error saving user: {}", userInDTO, e);
			throw e;
		}
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getUserByUserName(@RequestParam(value = "username") String userName) {
		UserOutDTO userOutDTO = this.userService.findByUserName(userName);
		log.info("Found user {} by username", userOutDTO);
		return ResponseEntity.ok().body(userOutDTO);
	}
	
}
