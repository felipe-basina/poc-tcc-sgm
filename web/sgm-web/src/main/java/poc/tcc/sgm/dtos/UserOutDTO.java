package poc.tcc.sgm.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class UserOutDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
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
	
	@Getter
	private String status;
	
	@Getter
	@JsonProperty(value = "tipo_documento")
	private String documentType;
	
	@JsonProperty(value = "nro_documento")
	private String documentNumber;
	
	@Getter
	@JsonProperty(value = "data_criacao")
	private String creationDate;
	
	@Getter
	@JsonProperty(value = "data_atualizacao")
	private String updateDate;
	
	@Getter
	@JsonProperty(value = "perfil")
	private String profile;

}
