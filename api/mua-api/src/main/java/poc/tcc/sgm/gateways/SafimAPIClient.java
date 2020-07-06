package poc.tcc.sgm.gateways;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poc.tcc.sgm.configs.FeignLoggingConfig;
import poc.tcc.sgm.dtos.CheckUserResponse;

@FeignClient(name = SafimAPIClient.CLIENT_NAME, url = "${api.gateway.safim}", configuration = FeignLoggingConfig.class)
public interface SafimAPIClient {

	public static final String CLIENT_NAME = "SafimAPIClient";
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CheckUserResponse checkServerUser(@RequestParam(value = "document") String document);
	
}
