package poc.tcc.sgm.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.forms.UserForm;
import poc.tcc.sgm.gateways.MuaAPIClient;

@Slf4j
@Controller
public class UserController {

	private MuaAPIClient muaAPIClient;
	
	@Autowired
	private UserController(MuaAPIClient muaAPIClient) {
		this.muaAPIClient = muaAPIClient;
	}
	
	@PostMapping(value = "/registerUser")
	public String registerUser(@Valid UserForm userForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", userForm);
			return "register";
		}
		
		if (userForm.valiationFields().isPresent()) {
			model.addAttribute("userForm", userForm);
			model.addAttribute("errorMessage", userForm.valiationFields().get());
			return "register";
		}
		
		try {
			this.muaAPIClient.createUser(userForm.convertToDto());
		} catch (Exception ex) {
			log.error("Error creating user {}", userForm, ex);
			model.addAttribute("userForm", userForm);
			model.addAttribute("errorMessage", ex.getMessage());
			return "register";
		}
		
		log.info("Registering user {}", userForm);
		return "redirect:/afterRegistration";
	}

}
