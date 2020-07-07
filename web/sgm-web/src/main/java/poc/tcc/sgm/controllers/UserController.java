package poc.tcc.sgm.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.forms.UserForm;

@Slf4j
@Controller
public class UserController {

	@PostMapping(value = "/registerUser")
	public String registerUser(@Valid UserForm userForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", userForm);
			return "register";
		}
		log.info("Registering user {}", userForm);
		return "login";
	}
	
}
