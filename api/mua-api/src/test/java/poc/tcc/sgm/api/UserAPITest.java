package poc.tcc.sgm.api;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import poc.tcc.sgm.api.advices.UserAPIAdvice;
import poc.tcc.sgm.constants.DocumentType;
import poc.tcc.sgm.constants.UserStatusType;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.exceptions.UserAlreadyExistsException;
import poc.tcc.sgm.exceptions.UserNotFoundException;
import poc.tcc.sgm.models.User;
import poc.tcc.sgm.models.UserProfile;
import poc.tcc.sgm.services.UserProfileService;
import poc.tcc.sgm.services.UserService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles(value = { "test" })
public class UserAPITest {

	@Mock
	private UserService userService;
	
	private String usersContext = "/users";
	
	private UserAPI userAPI;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		this.userAPI = new UserAPI(this.userService);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.userAPI)
				.setControllerAdvice(new UserAPIAdvice())
				.addPlaceholderValue("poc.tcc.sgm.api.user.api", this.usersContext)
			.build();
	}

	@Test
	public void testCreateUserWithServidorProfile() throws Exception {
		UserInDTO userInDTO = this.createUserInDTO();
		User user = this.getUser(userInDTO, this.servidor().get());
		
		Mockito.when(this.userService.createUser(userInDTO)).thenReturn(user.convertToDto());
		
		this.mockMvc.perform(post(this.usersContext)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(this.toJson(userInDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(57)))
				.andExpect(jsonPath("$.nome", is("FIRSTNAME")))
				.andExpect(jsonPath("$.sobrenome", is("LASTNAME")))
				.andExpect(jsonPath("$.email", is("new-user@domain.com")))
				.andExpect(jsonPath("$.usuario", is("new-user")))
				.andExpect(jsonPath("$.senha", is("122233")))
				.andExpect(jsonPath("$.status", is(UserStatusType.ACTIVE.name())))
				.andExpect(jsonPath("$.perfil", is("SERVIDOR")))
				.andExpect(jsonPath("$.nro_documento", is("77744422211")))
				.andExpect(jsonPath("$.tipo_documento", is("CPF")))
				.andExpect(jsonPath("$.data_criacao").exists())
				.andExpect(jsonPath("$.data_atualizacao").exists())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateUserWithMunicipeProfile() throws Exception {
		UserInDTO userInDTO = this.createUserInDTO();
		userInDTO.setDocumentNumber("77744422212");
		User user = this.getUser(userInDTO, this.municipe().get());
		
		Mockito.when(this.userService.createUser(userInDTO)).thenReturn(user.convertToDto());
		
		this.mockMvc.perform(post(this.usersContext)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(this.toJson(userInDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(57)))
				.andExpect(jsonPath("$.nome", is("FIRSTNAME")))
				.andExpect(jsonPath("$.sobrenome", is("LASTNAME")))
				.andExpect(jsonPath("$.email", is("new-user@domain.com")))
				.andExpect(jsonPath("$.usuario", is("new-user")))
				.andExpect(jsonPath("$.senha", is("122233")))
				.andExpect(jsonPath("$.status", is(UserStatusType.ACTIVE.name())))
				.andExpect(jsonPath("$.perfil", is("MUNICIPE")))
				.andExpect(jsonPath("$.nro_documento", is("77744422212")))
				.andExpect(jsonPath("$.tipo_documento", is("CPF")))
				.andExpect(jsonPath("$.data_criacao").exists())
				.andExpect(jsonPath("$.data_atualizacao").exists())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateUserBadRequestWithUserAlreadyExists() throws Exception {
		UserInDTO userInDTO = this.createUserInDTO();
		userInDTO.setDocumentNumber("77744422212");
		
		final String errorMessage = String.format("Usuário %s já cadastrado!", userInDTO.getUserName());
		
		Mockito.doThrow(new UserAlreadyExistsException(errorMessage)).when(this.userService)
			.createUser(userInDTO);
		
		this.mockMvc.perform(post(this.usersContext)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(this.toJson(userInDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
				.andExpect(jsonPath("$.message", is(errorMessage)))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateUserBadRequest() throws Exception {
		this.mockMvc.perform(post(this.usersContext)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(this.toJson(new UserInDTO())))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
				.andExpect(jsonPath("$.message", containsString("pode ser nulo ou vazio")))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetUserByUserName() throws Exception {
		UserInDTO userInDTO = this.createUserInDTO();
		User user = this.getUser(userInDTO, this.servidor().get());
		
		Mockito.when(this.userService.findByUserName(userInDTO.getUserName())).thenReturn(user.convertToDto());
		
		this.mockMvc.perform(get(this.usersContext).param("username", userInDTO.getUserName()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(57)))
				.andExpect(jsonPath("$.nome", is("FIRSTNAME")))
				.andExpect(jsonPath("$.sobrenome", is("LASTNAME")))
				.andExpect(jsonPath("$.email", is("new-user@domain.com")))
				.andExpect(jsonPath("$.usuario", is("new-user")))
				.andExpect(jsonPath("$.senha", is("122233")))
				.andExpect(jsonPath("$.status", is(UserStatusType.ACTIVE.name())))
				.andExpect(jsonPath("$.perfil", is("SERVIDOR")))
				.andExpect(jsonPath("$.nro_documento", is("77744422211")))
				.andExpect(jsonPath("$.tipo_documento", is("CPF")))
				.andExpect(jsonPath("$.data_criacao").exists())
				.andExpect(jsonPath("$.data_atualizacao").exists())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetUserByUserNameNotFound() throws Exception {
		final String userName = "a-not-found-user";
		
		final String errorMessage = String.format("Usuário %s não encontrado!", userName);
		
		Mockito.doThrow(new UserNotFoundException(errorMessage)).when(this.userService)
			.findByUserName(userName);
		
		this.mockMvc.perform(get(this.usersContext).param("username", userName))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
				.andExpect(jsonPath("$.message", is(errorMessage)))
			.andDo(MockMvcResultHandlers.print());
	}
	
	private UserInDTO createUserInDTO() {
		return UserInDTO.builder()
				.documentNumber("77744422211")
				.documentType(DocumentType.CPF.name())
				.email("new-user@domain.com")
				.lastName("LASTNAME")
				.name("FIRSTNAME")
				.password("122233")
				.userName("new-user")
			.build();
	}
	
	private User getUser(UserInDTO userInDTO, UserProfile userProfile) {
		User user = userInDTO.convertToNewEntity(userProfile);
		user.setId(57L);
		return user;
	}
	
	private Optional<UserProfile> municipe() {
		UserProfile municipe = new UserProfile(UserProfileService.MUNICIPE_ID, "MUNICIPE", null);
		return Optional.of(municipe);
	}
	
	private Optional<UserProfile> servidor() {
		UserProfile servidor = new UserProfile(UserProfileService.SERVIDOR_ID, "SERVIDOR", null);
		return Optional.of(servidor);
	}
	
	private String toJson(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

}
