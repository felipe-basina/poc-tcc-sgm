package poc.tcc.sgm.forms;

import java.util.Objects;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.tcc.sgm.dtos.UserInDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

	private static final String REGEX = "(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]+";
	
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
	
	private boolean samePassword() {
		if (Objects.isNull(this.senha) || Objects.isNull(this.confirmaSenha)) {
			return Boolean.FALSE;
		}
		if (!this.senha.equals(this.confirmaSenha)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	private boolean validPassword() {
		return this.senha.matches(REGEX);
	}
	
	public Optional<String> valiationFields() {
		if (!this.samePassword()) {
			return Optional.of("As senhas digitadas não conferem!");
		}
		if (!this.validPassword()) {
			return Optional.of("A senha deve ter pelo menos 8 caracteres somente com letras e números");
		}
		return Optional.empty();
	}
	
	public UserInDTO convertToDto() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return UserInDTO.builder()
				.documentNumber(this.numeroDocumento.replace(".", "")
						.replace("-", "")
						.replace("/", "")
						.replace(" ", "").trim())
				.documentType(this.tipoDocumento.trim())
				.email(this.email.trim())
				.lastName(this.sobreNome.trim())
				.name(this.nome.trim())
				.password(bCryptPasswordEncoder.encode(this.senha))
				.userName(this.usuario.trim())
			.build();
	}
	
}
