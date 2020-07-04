package poc.tcc.sgm.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import poc.tcc.sgm.services.UserValidationService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserAPITest {

	@Autowired
	private UserValidationService userValidationService;
	
	@Value(value = "${poc.tcc.sgm.api.user.api}")
	private String usersContext;
	
	private UserAPI userAPI;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		this.userAPI = new UserAPI(this.userValidationService);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.userAPI)
				.addPlaceholderValue("poc.tcc.sgm.api.user.api", this.usersContext)
			.build();
	}

	@Test
	public void testCheckNotServerUser() throws Exception {
		final String document = "111.222.333-44";
		
		this.mockMvc.perform(get(this.usersContext).param("document", document))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.is_server_user", is(Boolean.FALSE)))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testCheckServerUser() throws Exception {
		final String document = "111.222.333-45";
		
		this.mockMvc.perform(get(this.usersContext).param("document", document))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.is_server_user", is(Boolean.TRUE)))
			.andDo(MockMvcResultHandlers.print());
	}

}
