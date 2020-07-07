package poc.tcc.sgm.forms;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String sobreNome;
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String usuario;
	
	@NotEmpty
	private String tipoDocumento;
	
	@NotEmpty
	private String numeroDocumento;
	
	@NotEmpty
	private String senha;
	
	@NotEmpty
	private String confirmaSenha;
	
}
