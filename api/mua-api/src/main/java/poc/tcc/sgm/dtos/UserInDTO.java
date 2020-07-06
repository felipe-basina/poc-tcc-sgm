package poc.tcc.sgm.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.tcc.sgm.constants.DocumentType;
import poc.tcc.sgm.constants.UserStatusType;
import poc.tcc.sgm.models.User;
import poc.tcc.sgm.models.UserProfile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Campo NOME não pode ser nulo ou vazio")
	@JsonProperty(value = "nome")
	private String name;
	
	@NotEmpty(message = "Campo SOBRENOME não pode ser nulo ou vazio")
	@JsonProperty(value = "sobrenome")
	private String lastName;
	
	@NotEmpty(message = "Campo EMAIL não pode ser nulo ou vazio")
	@JsonProperty(value = "email")
	private String email;

	@NotEmpty(message = "Campo USUARIO não pode ser nulo ou vazio")
	@JsonProperty(value = "usuario")
	private String userName;
	
	@NotEmpty(message = "Campo SENHA não pode ser nulo ou vazio")
	@JsonProperty(value = "senha")
	private String password;
	
	@NotEmpty(message = "Campo TIPO_DOCUMENTO não pode ser nulo ou vazio")
	@JsonProperty(value = "tipo_documento")
	private String documentType;
	
	@NotEmpty(message = "Campo NUMERO_DOCUMENTO não pode ser nulo ou vazio")
	@JsonProperty(value = "nro_documento")
	private String documentNumber;
	
	@Transient
	public User convertToNewEntity(UserProfile userProfile) {
		Date now = new Date();
		return User.builder()
				.creationDate(now)
				.documentNumber(this.documentNumber)
				.documentType(DocumentType.getDocumentType(this.documentType))
				.email(this.email)
				.lastName(this.lastName)
				.name(this.name)
				.password(this.password)
				.status(UserStatusType.ACTIVE)
				.profile(userProfile)
				.updateDate(now)
				.userName(this.userName)
			.build();
	}
	
	@Transient
	public boolean isCPFdocumentType() {
		return DocumentType.CPF.name().equalsIgnoreCase(this.documentType);
	}

}
