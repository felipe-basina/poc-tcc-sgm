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
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.tcc.sgm.constants.DocumentType;
import poc.tcc.sgm.constants.UserStatusType;
import poc.tcc.sgm.dtos.UserOutDTO;

@Data
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
	
	@Column(name = "SENHA")
	private String password;
	
	@Column(name = "STATUS")
	@Enumerated(value = EnumType.STRING)
	private UserStatusType status;
	
	@Column(name = "TIPO_DOCUMENTO")
	@Enumerated(value = EnumType.STRING)
	private DocumentType documentType;
	
	@Column(name = "NUMERO_DOCUMENTO")
	private String documentNumber;
	
	@Column(name = "DATA_CRIACAO")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date creationDate;
	
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
	
}
