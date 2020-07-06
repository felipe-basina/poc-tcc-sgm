package poc.tcc.sgm.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "nome")
	private String name;
	
	@JsonProperty(value = "sobrenome")
	private String lastName;
	
	@JsonProperty(value = "email")
	private String email;

	@JsonProperty(value = "usuario")
	private String userName;
	
	@JsonProperty(value = "senha")
	private String password;
	
	@JsonProperty(value = "tipo_documento")
	private String documentType;
	
	@JsonProperty(value = "nro_documento")
	private String documentNumber;

}
