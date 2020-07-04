package poc.tcc.sgm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.dto.UserValidationResponseDTO;
import poc.tcc.sgm.services.UserValidationService;

@Slf4j
@RestController
@RequestMapping(value = "${poc.tcc.sgm.api.user.api}")
public class UserAPI {

	private UserValidationService userValidationService;

	@Autowired
	public UserAPI(UserValidationService userValidationService) {
		this.userValidationService = userValidationService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserValidationResponseDTO> checkServerUser(
			@RequestParam(value = "document") String document) {
		UserValidationResponseDTO userValidationResponseDTO = new UserValidationResponseDTO(
				this.userValidationService.isServidorUser(document));
		log.info("Checking servidor user for {}. Response: {}", document, userValidationResponseDTO);
		return ResponseEntity.ok().body(userValidationResponseDTO);
	}

}
