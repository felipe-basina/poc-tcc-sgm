package poc.tcc.sgm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class UserValidationResponseDTO {

	@JsonProperty(value = "is_server_user")
	private boolean isServerUser;
	
}
