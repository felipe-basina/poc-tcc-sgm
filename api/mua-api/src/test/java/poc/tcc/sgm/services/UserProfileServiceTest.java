package poc.tcc.sgm.services;

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
import poc.tcc.sgm.dtos.CheckUserResponse;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.gateways.SafimAPIClient;
import poc.tcc.sgm.models.UserProfile;
import poc.tcc.sgm.repositories.UserProfileRepository;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = { "test" })
public class UserProfileServiceTest {

	@Mock
	private UserProfileRepository userProfileRepository;
	
	@Mock
	private SafimAPIClient safimAPIClient;
	
	private UserProfileService userProfileService;
	
	@Before
	public void setUp() throws Exception {
		this.userProfileService = new UserProfileService(this.userProfileRepository, this.safimAPIClient);
	}

	@Test
	public void testGetUserProfileNotServidor() {
		UserInDTO userInDTO = UserInDTO.builder()
				.documentNumber("11122233344")
				.documentType(DocumentType.CPF.name())
				.userName("a-municipe-user")
			.build();
		
		CheckUserResponse checkUserResponse = new CheckUserResponse(Boolean.FALSE);
		Mockito.when(this.safimAPIClient.checkServerUser(userInDTO.getDocumentNumber())).thenReturn(checkUserResponse);
	
		Mockito.when(this.userProfileRepository.findById(UserProfileService.MUNICIPE_ID)).thenReturn(this.municipe());
		
		UserProfile municipe = this.userProfileService.getUserProfile(userInDTO);
		Assert.assertEquals(this.municipe().get(), municipe);
		
		Mockito.verify(this.safimAPIClient, Mockito.times(1)).checkServerUser(userInDTO.getDocumentNumber());
		Mockito.verify(this.userProfileRepository, Mockito.times(1)).findById(UserProfileService.MUNICIPE_ID);
		Mockito.verifyNoMoreInteractions(this.safimAPIClient, this.userProfileRepository);
	}

	@Test
	public void testGetUserProfileNotServidorWithCNPJ() {
		UserInDTO userInDTO = UserInDTO.builder()
				.documentNumber("47326424000104")
				.documentType(DocumentType.CNPJ.name())
				.userName("a-municipe-user")
			.build();
		
		Mockito.when(this.userProfileRepository.findById(UserProfileService.MUNICIPE_ID)).thenReturn(this.municipe());
		
		UserProfile municipe = this.userProfileService.getUserProfile(userInDTO);
		Assert.assertEquals(this.municipe().get(), municipe);
		
		Mockito.verifyNoInteractions(this.safimAPIClient);
		Mockito.verify(this.userProfileRepository, Mockito.times(1)).findById(UserProfileService.MUNICIPE_ID);
		Mockito.verifyNoMoreInteractions(this.userProfileRepository);
	}

	@Test
	public void testGetUserProfileServidor() {
		UserInDTO userInDTO = UserInDTO.builder()
				.documentNumber("11122233345")
				.documentType(DocumentType.CPF.name())
				.userName("a-server-user")
			.build();
		
		CheckUserResponse checkUserResponse = new CheckUserResponse(Boolean.TRUE);
		Mockito.when(this.safimAPIClient.checkServerUser(userInDTO.getDocumentNumber())).thenReturn(checkUserResponse);
	
		Mockito.when(this.userProfileRepository.findById(UserProfileService.SERVIDOR_ID)).thenReturn(this.servidor());
		
		UserProfile servidor = this.userProfileService.getUserProfile(userInDTO);
		Assert.assertEquals(this.servidor().get(), servidor);
		
		Mockito.verify(this.safimAPIClient, Mockito.times(1)).checkServerUser(userInDTO.getDocumentNumber());
		Mockito.verify(this.userProfileRepository, Mockito.times(1)).findById(UserProfileService.SERVIDOR_ID);
		Mockito.verifyNoMoreInteractions(this.safimAPIClient, this.userProfileRepository);
	}
	
	private Optional<UserProfile> municipe() {
		UserProfile municipe = new UserProfile(UserProfileService.MUNICIPE_ID, "MUNICIPE", null);
		return Optional.of(municipe);
	}
	
	private Optional<UserProfile> servidor() {
		UserProfile servidor = new UserProfile(UserProfileService.SERVIDOR_ID, "SERVIDOR", null);
		return Optional.of(servidor);
	}

}
