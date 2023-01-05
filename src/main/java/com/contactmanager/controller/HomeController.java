package com.contactmanager.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.contactmanager.entity.User;
import com.contactmanager.helper.Messages;
import com.contactmanager.repository.UserRepository;

@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/home")
	public String homePage(Model model) {
		model.addAttribute("tittle", "mith - home contact page");
		return "home";
	}

	@GetMapping("/about")
	public String aboutPage(Model model) {
		model.addAttribute("tittle", "mith - about contact page");
		return "about";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("tittle", "login page");
		return "login";
	}

	@GetMapping("/signUp")
	public String signUp(Model model) {
		model.addAttribute("tittle", "signUp page");
		model.addAttribute("user", new User());
		return "signUp";
	}

	@RequestMapping(value = "/id_register", method = RequestMethod.POST)
	public String getRegister(@Valid @ModelAttribute("user") User user,BindingResult result,
			@RequestParam(value = "check", defaultValue = "false") boolean check, Model model, HttpSession session) {
		try {
			if (!check) {
				System.out.println("u have not aggred with term and condition");
				throw new Exception("u have not aggred with term and condition");
			}
			if(result.hasErrors()) {
				model.addAttribute("user", user);
				return "signUp";
			}
			user.setRole("ROLE_USER");
			user.setEnable(true);
			user.setImageUrl("default.jpg");
			this.userRepo.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message" + "successfull registered ! !", "alert-success");
			return "signUp";
		} catch (Exception e) {
			model.addAttribute("user", user);
			session.setAttribute("message", new Messages("smothing went wrong, " + e.getMessage(), "alert-danger"));
			return "signUp";
		}

	}
}
