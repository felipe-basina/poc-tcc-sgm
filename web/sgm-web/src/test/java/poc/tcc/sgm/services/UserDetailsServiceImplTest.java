package poc.tcc.sgm.services;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import poc.tcc.sgm.dtos.UserOutDTO;
import poc.tcc.sgm.gateways.MuaAPIClient;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = { "test" })
public class UserDetailsServiceImplTest {

	@Mock
	private MuaAPIClient muaAPIClient;
	
	@InjectMocks
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLoadUserByUsernameOneFound() {
		final String username = "j-unit";
		UserOutDTO userOutDTO = this.createUserOutDTO();
		
		Mockito.when(this.muaAPIClient.findUserByUsername(username)).thenReturn(userOutDTO);
		
		UserDetails applicationUser = this.userDetailsServiceImpl.loadUserByUsername(username);
		Assert.assertNotNull(applicationUser);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameNotFound() {
		final String username = "j-unit";
		
		Mockito.doThrow(new RuntimeException()).when(this.muaAPIClient).findUserByUsername(username);
		
		this.userDetailsServiceImpl.loadUserByUsername(username);
	}
	
	private UserOutDTO createUserOutDTO() {
		return UserOutDTO.builder()
				.id(1L)
				.creationDate(LocalDateTime.now().toString())
				.documentNumber("11122233343")
				.documentType("CPF")
				.email("junit@domain.com")
				.lastName("Test")
				.name("Junit")
				.password("$#212sdasdasdasdsad#$")
				.profile("SERVIDOR")
				.status("ACTIVE")
				.updateDate(LocalDateTime.now().toString())
				.userName("j-unit")
			.build();
	}

}
