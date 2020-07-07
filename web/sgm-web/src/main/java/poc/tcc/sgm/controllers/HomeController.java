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

	@GetMapping(value = "${poc.tcc.sgm.api.home.root}")
	public String root() {
		return "home";
	}

	@GetMapping(value = "${poc.tcc.sgm.api.home.login}")
	public String login() {
		return "login";
	}

	@GetMapping(value = "${poc.tcc.sgm.api.home.register}")
	public String register(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "register";
	}

	@GetMapping(value = "${poc.tcc.sgm.api.home.afterRegistration}")
	public RedirectView afterRegistration(HttpServletRequest req, RedirectAttributes redir) {
		RedirectView redirectView = new RedirectView("/login", true);
		redir.addFlashAttribute("register", "ok");
		return redirectView;
	}

}
