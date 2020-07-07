package poc.tcc.sgm.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.dtos.ApiErrorMessage;
import poc.tcc.sgm.forms.UserForm;
import poc.tcc.sgm.gateways.MuaAPIClient;

@Slf4j
@Controller
public class UserController {

	private MuaAPIClient muaAPIClient;
	
	@Autowired
	public UserController(MuaAPIClient muaAPIClient) {
		this.muaAPIClient = muaAPIClient;
	}
	
	@PostMapping(value = "${poc.tcc.sgm.api.user}")
	public String registerUser(@Valid UserForm userForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", userForm);
			return "register";
		}
		
		if (userForm.validationFields().isPresent()) {
			model.addAttribute("userForm", userForm);
			model.addAttribute("errorMessage", userForm.validationFields().get());
			return "register";
		}
		
		try {
			this.muaAPIClient.createUser(userForm.convertToDto());
		} catch (Exception ex) {
			log.error("Error creating user {}", userForm, ex);
			model.addAttribute("userForm", userForm);
			model.addAttribute("errorMessage", this.getApiErrorMessage(ex).getMessage());
			return "register";
		}
		
		log.info("Registering user {}", userForm);
		return "redirect:/afterRegistration";
	}
	
	private ApiErrorMessage getApiErrorMessage(Exception ex) {
		String message = ex.getMessage();
		int index = message.indexOf(": ");
		String substring = message.substring(index + 2);
		try {
			ApiErrorMessage[] apiErrorMessage = new ObjectMapper().readValue(substring, ApiErrorMessage[].class);
			return apiErrorMessage[0];
		} catch (JsonProcessingException e) {
			log.error("{}", e);
			return new ApiErrorMessage(LocalDateTime.now().toString(), HttpStatus.BAD_REQUEST.value(),
					"Erro ao cadastrar usuario");
		}
	}
	
}
