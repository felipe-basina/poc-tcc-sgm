package poc.tcc.sgm.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import poc.tcc.sgm.dtos.ApiErrorMessage;
import poc.tcc.sgm.dtos.UserOutDTO;
import poc.tcc.sgm.forms.UserForm;
import poc.tcc.sgm.gateways.MuaAPIClient;

@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles(value = { "test" })
public class UserControllerTest {
	
	@Mock
	private MuaAPIClient muaAPIClient;
	
	private UserController userController;
	
	@Mock
	private View mockView;
	
	private MockMvc mockMvc;
	
	private final String userContext = "/registerUser";

	@Before
	public void setUp() throws Exception {
		this.userController = new UserController(this.muaAPIClient);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.userController)
				.setSingleView(this.mockView)
				.addPlaceholderValue("poc.tcc.sgm.api.user", this.userContext)
			.build();
	}

	@Test
	public void testRegisterUser() throws Exception {
		UserForm userForm = this.createUserForm();
		
		Mockito.when(this.muaAPIClient.createUser(userForm.convertToDto()))
			.thenReturn(this.createUserOutDTO());
		
		mockMvc.perform(post(this.userContext)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("confirmaSenha", userForm.getConfirmaSenha())
                .param("email", userForm.getEmail())
                .param("nome", userForm.getNome())
                .param("numeroDocumento", userForm.getNumeroDocumento())
                .param("senha", userForm.getSenha())
                .param("sobreNome", userForm.getSobreNome())
                .param("tipoDocumento", userForm.getTipoDocumento())
                .param("usuario", userForm.getUsuario()))
			.andExpect(status().is2xxSuccessful())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testRegisterUserBadRequestPasswordsDontMatch() throws Exception {
		UserForm userForm = this.createUserForm();
		userForm.setConfirmaSenha("Senha12");
		
		mockMvc.perform(post(this.userContext)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("confirmaSenha", userForm.getConfirmaSenha())
                .param("email", userForm.getEmail())
                .param("nome", userForm.getNome())
                .param("numeroDocumento", userForm.getNumeroDocumento())
                .param("senha", userForm.getSenha())
                .param("sobreNome", userForm.getSobreNome())
                .param("tipoDocumento", userForm.getTipoDocumento())
                .param("usuario", userForm.getUsuario()))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeExists("userForm"))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(view().name("register"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testRegisterUserBadRequestEmptyForm() throws Exception {
		UserForm userForm = this.createUserForm();
		userForm.setConfirmaSenha("Senha12");
		
		mockMvc.perform(post(this.userContext)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeExists("userForm"))
			.andExpect(view().name("register"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testRegisterUserInternalServerErrorWhenCreatingUser() throws Exception {
		UserForm userForm = this.createUserForm();
		
		Mockito.doThrow(new RuntimeException("Error....")).when(this.muaAPIClient).createUser(userForm.convertToDto());
		
		mockMvc.perform(post(this.userContext)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("confirmaSenha", userForm.getConfirmaSenha())
                .param("email", userForm.getEmail())
                .param("nome", userForm.getNome())
                .param("numeroDocumento", userForm.getNumeroDocumento())
                .param("senha", userForm.getSenha())
                .param("sobreNome", userForm.getSobreNome())
                .param("tipoDocumento", userForm.getTipoDocumento())
                .param("usuario", userForm.getUsuario()))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeExists("userForm"))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(view().name("register"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testRegisterUserInternalServerErrorWhenCreatingUserWithApiErrorMessage() throws Exception {
		ApiErrorMessage apiErrorMessage = new ApiErrorMessage(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST.value(), "Error message");
		final String message = String.format("Error message from api: [%s]", new ObjectMapper().writeValueAsString(apiErrorMessage));
		
		UserForm userForm = this.createUserForm();
		
		Mockito.doThrow(new RuntimeException(message)).when(this.muaAPIClient).createUser(userForm.convertToDto());
		
		mockMvc.perform(post(this.userContext)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("confirmaSenha", userForm.getConfirmaSenha())
                .param("email", userForm.getEmail())
                .param("nome", userForm.getNome())
                .param("numeroDocumento", userForm.getNumeroDocumento())
                .param("senha", userForm.getSenha())
                .param("sobreNome", userForm.getSobreNome())
                .param("tipoDocumento", userForm.getTipoDocumento())
                .param("usuario", userForm.getUsuario()))
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeExists("userForm"))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(view().name("register"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	private UserForm createUserForm() {
		return UserForm.builder()
				.confirmaSenha("Senha123")
				.email("novoemail@dominio.com.br")
				.nome("Novo")
				.numeroDocumento("78965432111")
				.senha("Senha123")
				.sobreNome("Usuario")
				.tipoDocumento("CPF")
				.usuario("n-usuario")
			.build();
	}
	
	private UserOutDTO createUserOutDTO() {
		return UserOutDTO.builder()
				.id(1L)
				.creationDate(LocalDateTime.now().toString())
				.documentNumber("78965432111")
				.documentType("CPF")
				.email("novoemail@dominio.com.br")
				.lastName("Usuario")
				.name("Novo")
				.password("$#212sdasdasdasdsad#$")
				.profile("SERVIDOR")
				.status("ACTIVE")
				.updateDate(LocalDateTime.now().toString())
				.userName("n-usuario")
			.build();
	}

}
