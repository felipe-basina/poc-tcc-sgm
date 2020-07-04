package poc.tcc.sgm.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserValidationServiceTest {

	private UserValidationService userValidationService;
	
	@Before
	public void setUp() throws Exception {
		this.userValidationService = new UserValidationService();
	}

	@Test
	public void testIsValidCpfWithEspecialCharacters() {
		final String cpfWithEspecialCharacters = "111.222.333-44";
		Assert.assertTrue(this.userValidationService.isValidCpf(cpfWithEspecialCharacters));
	}

	@Test
	public void testIsValidCpfWithoutEspecialCharacters() {
		final String cpfWithEspecialCharacters = "11122233344";
		Assert.assertTrue(this.userValidationService.isValidCpf(cpfWithEspecialCharacters));
	}

	@Test
	public void testIsValidCpfWithLessAllowedCharacters() {
		final String cpfWithEspecialCharacters = "111.222.333-4";
		Assert.assertFalse(this.userValidationService.isValidCpf(cpfWithEspecialCharacters));
	}

	@Test
	public void testIsValidCpfWithNullDocument() {
		final String cpfWithEspecialCharacters = null;
		Assert.assertFalse(this.userValidationService.isValidCpf(cpfWithEspecialCharacters));
	}

	@Test
	public void testIsValidCpfWithEmptyDocument() {
		final String cpfWithEspecialCharacters = "";
		Assert.assertFalse(this.userValidationService.isValidCpf(cpfWithEspecialCharacters));
	}

}
