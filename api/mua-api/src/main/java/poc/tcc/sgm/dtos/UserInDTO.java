package poc.tcc.sgm.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import poc.tcc.sgm.constants.DocumentType;
import poc.tcc.sgm.constants.UserStatusType;
import poc.tcc.sgm.models.User;
import poc.tcc.sgm.models.UserProfile;

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
