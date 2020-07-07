package poc.tcc.sgm.gateways;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import poc.tcc.sgm.configs.FeignLoggingConfig;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.dtos.UserOutDTO;

@FeignClient(name = MuaAPIClient.CLIENT_NAME, url = "${api.gateway.mua}", configuration = FeignLoggingConfig.class)
public interface MuaAPIClient {

	public static final String CLIENT_NAME = "MuaAPIClient";
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public UserOutDTO findUserByUsername(@RequestParam(value = "username") String username);
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserOutDTO createUser(@RequestBody UserInDTO userInDTO);
	
}
