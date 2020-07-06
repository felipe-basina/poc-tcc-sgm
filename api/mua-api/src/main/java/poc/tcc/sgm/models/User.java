package poc.tcc.sgm.models;

import java.io.Serializable;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.tcc.sgm.constants.DocumentType;
import poc.tcc.sgm.constants.UserStatusType;

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
	
}
