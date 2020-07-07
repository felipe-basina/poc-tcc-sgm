package poc.tcc.sgm.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles(value = { "test" })
public class HomeControllerTest {

	private HomeController homeController;
	
	@Mock
	private View mockView;
	
	private MockMvc mockMvc;
	
	private final String rootContext = "/";
	private final String loginContext = "/login";
	private final String registerContext = "/register";
	private final String afterRegistrationContext = "/afterRegistration";
	
	@Before
	public void setUp() throws Exception {
		this.homeController = new HomeController();
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.homeController)
				.setSingleView(this.mockView)
				.addPlaceholderValue("poc.tcc.sgm.api.home.root", this.rootContext)
				.addPlaceholderValue("poc.tcc.sgm.api.home.login", this.loginContext)
				.addPlaceholderValue("poc.tcc.sgm.api.home.register", this.registerContext)
				.addPlaceholderValue("poc.tcc.sgm.api.home.afterRegistration", this.afterRegistrationContext)
			.build();
	}

	@Test
	public void testRoot() throws Exception {
		this.mockMvc.perform(get(this.rootContext))
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
	}

	@Test
	public void testLogin() throws Exception {
		this.mockMvc.perform(get(this.loginContext))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}

	@Test
	public void testRegister() throws Exception {
		this.mockMvc.perform(get(this.registerContext))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("userForm"))
			.andExpect(view().name("register"));
	}

	@Test
	public void testAfterRegistration() throws Exception {
		this.mockMvc.perform(get(this.afterRegistrationContext))
			.andExpect(status().is3xxRedirection())
            .andExpect(flash().attribute("register", is("ok")))
			.andExpect(redirectedUrl("/login"));
	}

}
