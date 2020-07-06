package poc.tcc.sgm.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.exceptions.UserAlreadyExistsException;
import poc.tcc.sgm.repositories.UserRepository;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(value = { "test" })
public class UserVerificationServiceTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MessageSource message;
	
	private UserVerificationService userVerificationService;
	
	@Before
	public void setUp() throws Exception {
		this.userVerificationService = new UserVerificationService(this.userRepository, this.message);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void testCheckUserInvalidByUserName() {
		UserInDTO userInDTO = UserInDTO.builder()
				.userName("j-santocristo")
				.email("santocristo.joao@dominio.com")
				.documentNumber("12332111122")
			.build();
		this.userVerificationService.checkUser(userInDTO);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void testCheckUserInvalidByEmail() {
		UserInDTO userInDTO = UserInDTO.builder()
				.userName("j-santocristo1")
				.email("santocristo.joao@dominio.com")
				.documentNumber("12332111122")
			.build();
		this.userVerificationService.checkUser(userInDTO);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void testCheckUserInvalidByDocumentNumber() {
		UserInDTO userInDTO = UserInDTO.builder()
				.userName("j-santocristo1")
				.email("santocristo.joao1@dominio.com")
				.documentNumber("12332111122")
			.build();
		this.userVerificationService.checkUser(userInDTO);
	}

	@Test
	public void testCheckUser() {
		UserInDTO userInDTO = UserInDTO.builder()
				.userName("j-santocristo2")
				.email("santocristo.joao@dominio.com2")
				.documentNumber("12332111111")
			.build();
		this.userVerificationService.checkUser(userInDTO);
		Assert.assertTrue(Boolean.TRUE);
	}

}
