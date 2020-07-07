package poc.tcc.sgm.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import poc.tcc.sgm.forms.UserForm;

@Controller
public class HomeController {

	@GetMapping("/")
	public String root() {
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "register";
	}

	@GetMapping(value = "/afterRegistration")
	public RedirectView loginValidate(HttpServletRequest req, RedirectAttributes redir) {
		RedirectView redirectView = new RedirectView("/login", true);
		redir.addFlashAttribute("register", "ok");
		return redirectView;
	}

}
