package poc.tcc.sgm.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

	@JsonProperty("timestamp")
	private String timestamp = null;

	@JsonProperty("status")
	private Integer status = null;

	@JsonProperty("message")
	private String message = null;
	
}
