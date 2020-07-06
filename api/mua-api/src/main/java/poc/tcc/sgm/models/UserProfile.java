package poc.tcc.sgm.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERFIL")
public class UserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4243577294014247767L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NOME")
	private String name;
	
	@Exclude
	@OneToOne(mappedBy = "profile")
	private User user;

}
