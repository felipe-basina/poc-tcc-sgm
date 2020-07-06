package poc.tcc.sgm.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CheckUserResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 670971362729207546L;

	@JsonProperty(value = "is_server_user")
	private Boolean serverUser;
	
}
