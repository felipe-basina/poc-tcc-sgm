package poc.tcc.sgm.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poc.tcc.sgm.constants.DocumentType;
import poc.tcc.sgm.constants.UserStatusType;
import poc.tcc.sgm.dtos.UserOutDTO;

@Getter
@Setter
@Builder
@Entity
@Table(name = "USUARIO")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8797453093033664660L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NOME")
	private String name;
	
	@Column(name = "SOBRENOME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "USUARIO")
	private String userName;
	
	@Exclude
	@Column(name = "SENHA")
	private String password;
	
	@Exclude
	@Column(name = "STATUS")
	@Enumerated(value = EnumType.STRING)
	private UserStatusType status;
	
	@Column(name = "TIPO_DOCUMENTO")
	@Enumerated(value = EnumType.STRING)
	private DocumentType documentType;
	
	@Column(name = "NUMERO_DOCUMENTO")
	private String documentNumber;
	
	@Exclude
	@Column(name = "DATA_CRIACAO")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Exclude
	@Column(name = "DATA_ATUALIZACAO")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERFIL_ID", referencedColumnName = "id")
	private UserProfile profile;

	@Transient
	private LocalDateTime convertToLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	@Transient
	public UserOutDTO convertToDto() {
		return UserOutDTO.builder()
				.creationDate(this.convertToLocalDateTime(this.getCreationDate()).toString())
				.documentNumber(this.getDocumentNumber())
				.documentType(this.getDocumentType().name())
				.email(this.getEmail())
				.id(this.getId())
				.lastName(this.getLastName())
				.name(this.getName())
				.password(this.getPassword())
				.profile(this.getProfile().getName())
				.status(this.getStatus().name())
				.updateDate(this.convertToLocalDateTime(this.getUpdateDate()).toString())
				.userName(this.getUserName())
			.build();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentNumber == null) ? 0 : documentNumber.hashCode());
		result = prime * result + ((documentType == null) ? 0 : documentType.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (documentNumber == null) {
			if (other.documentNumber != null)
				return false;
		} else if (!documentNumber.equals(other.documentNumber))
			return false;
		if (documentType != other.documentType)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
