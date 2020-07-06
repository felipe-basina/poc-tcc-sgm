package poc.tcc.sgm.services;

import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import poc.tcc.sgm.constants.DocumentType;
import poc.tcc.sgm.constants.UserStatusType;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.dtos.UserOutDTO;
import poc.tcc.sgm.exceptions.UserNotFoundException;
import poc.tcc.sgm.models.User;
import poc.tcc.sgm.models.UserProfile;
import poc.tcc.sgm.repositories.UserRepository;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = { "test" })
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserVerificationService userVerificationService;
	
	@Mock
	private UserProfileService userProfileService;
	
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
		this.userService = new UserService(this.userRepository, this.userVerificationService, this.userProfileService);
	}

	@Test
	public void testCreateUser() {
		UserInDTO newUser = this.createUserInDTO();
		
		Mockito.doNothing().when(this.userVerificationService).checkUser(newUser);
		
		UserProfile servidor = this.servidor().get();
		Mockito.when(this.userProfileService.getUserProfile(newUser)).thenReturn(servidor);
		
		User userCreated = newUser.convertToNewEntity(servidor);
		User userCreatedResponse = this.userCreated(servidor);
		Mockito.when(this.userRepository.save(userCreated)).thenReturn(userCreatedResponse);
		
		UserOutDTO userResponse = this.userService.createUser(newUser);
		Assert.assertNotNull(userResponse);
		Assert.assertNotNull(userResponse.getProfile());
		
		Mockito.verify(this.userVerificationService, Mockito.times(1)).checkUser(newUser);
		Mockito.verify(this.userProfileService, Mockito.times(1)).getUserProfile(newUser);
		Mockito.verify(this.userRepository, Mockito.times(1)).save(userCreated);
		Mockito.verifyNoMoreInteractions(this.userVerificationService, this.userProfileService, this.userRepository);
	}

	@Test
	public void testFindByUserName() {
		final String userName = "new-user@domain.com";
		UserProfile servidor = this.servidor().get();
		User user = this.userCreated(servidor);
		Mockito.when(this.userVerificationService.findByUserName(userName)).thenReturn(user.convertToDto());
		
		UserOutDTO userResponse = this.userService.findByUserName(userName);
		Assert.assertNotNull(userResponse);
		Assert.assertNotNull(userResponse.getProfile());
		
		Mockito.verify(this.userVerificationService, Mockito.times(1)).findByUserName(userName);
		Mockito.verifyNoMoreInteractions(this.userVerificationService);
		Mockito.verifyNoInteractions(this.userProfileService, this.userRepository);
	}

	@Test(expected = UserNotFoundException.class)
	public void testFindByUserNameNotFound() {
		final String userName = "new-user@domain.com";
		
		Mockito.doThrow(new UserNotFoundException("User not found"))
			.when(this.userVerificationService)
			.findByUserName(userName);
		
		this.userService.findByUserName(userName);
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
	
	private Optional<UserProfile> servidor() {
		UserProfile servidor = new UserProfile(UserProfileService.SERVIDOR_ID, "SERVIDOR", null);
		return Optional.of(servidor);
	}
	
	private User userCreated(UserProfile userProfile) {
		Date now = new Date();
		return User.builder()
				.creationDate(now)
				.updateDate(now)
				.documentNumber("77744422211")
				.documentType(DocumentType.CPF)
				.email("new-user@domain.com")
				.lastName("LASTNAME")
				.name("FIRSTNAME")
				.password("122233")
				.userName("new-user")
				.id(17L)
				.profile(userProfile)
				.status(UserStatusType.ACTIVE)
			.build();
	}

}
