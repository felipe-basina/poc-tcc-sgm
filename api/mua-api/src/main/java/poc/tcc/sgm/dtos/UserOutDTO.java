package poc.tcc.sgm.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import poc.tcc.sgm.constants.DocumentType;
import poc.tcc.sgm.constants.UserStatusType;

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
	
	public UserOutDTO setCreationDate(Date creationDate) {
		this.creationDate = this.convertToLocalDateTime(creationDate).toString();
		return this;
	}
	
	public UserOutDTO setUpdateDate(Date updateDate) {
		this.updateDate = this.convertToLocalDateTime(updateDate).toString();
		return this;
	}
	
	public UserOutDTO setDocumentType(DocumentType documentType) {
		this.documentType = documentType.name();
		return this;
	}
	
	public UserOutDTO setStatus(UserStatusType userStatusType) {
		this.status = userStatusType.name();
		return this;
	}
	
	private LocalDateTime convertToLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
