package poc.tcc.sgm.repositories;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import poc.tcc.sgm.models.User;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = { "test" })
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindByUserName() {
		Optional<User> joaoSantoCristoOptional = this.userRepository.findByUserName("j-santocristo");
		Assert.assertTrue(joaoSantoCristoOptional.isPresent());
		
		User joaoSantoCristo = joaoSantoCristoOptional.get();
		Assert.assertNotNull(joaoSantoCristo.getProfile());
	}

	@Test
	public void testFindByEmail() {
		Optional<User> joaoSantoCristoOptional = this.userRepository.findByEmail("santocristo.joao@dominio.com");
		Assert.assertTrue(joaoSantoCristoOptional.isPresent());
		
		User joaoSantoCristo = joaoSantoCristoOptional.get();
		Assert.assertNotNull(joaoSantoCristo.getProfile());
	}

	@Test
	public void testFindByDocumentNumber() {
		Optional<User> joaoSantoCristoOptional = this.userRepository.findByDocumentNumber("12332111122");
		Assert.assertTrue(joaoSantoCristoOptional.isPresent());
		
		User joaoSantoCristo = joaoSantoCristoOptional.get();
		Assert.assertNotNull(joaoSantoCristo.getProfile());
	}

	@Test
	public void testFindByUserNameNotFound() {
		Optional<User> elPabloOptional = this.userRepository.findByUserName("el-pablo");
		Assert.assertFalse(elPabloOptional.isPresent());
	}

}
