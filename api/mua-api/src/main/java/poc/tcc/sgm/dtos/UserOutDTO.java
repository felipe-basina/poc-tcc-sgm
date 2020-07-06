package poc.tcc.sgm.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import poc.tcc.sgm.models.User;

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
	
	private LocalDateTime convertToLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	@Transient
	public UserOutDTO convertFromEntity(User user) {
		return UserOutDTO.builder()
				.creationDate(this.convertToLocalDateTime(user.getCreationDate()).toString())
				.documentNumber(user.getDocumentNumber())
				.documentType(user.getDocumentType().name())
				.email(user.getEmail())
				.id(user.getId())
				.lastName(user.getLastName())
				.name(user.getName())
				.password(user.getPassword())
				.profile(user.getProfile().getName())
				.status(user.getStatus().name())
				.updateDate(this.convertToLocalDateTime(user.getUpdateDate()).toString())
				.userName(user.getUserName())
			.build();
	}

}
