package com.taskmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.taskmaster.entity.AppUser;
import com.taskmaster.enums.AppUserRole;
import com.taskmaster.model.RegisterationRequest;
import com.taskmaster.service.AppUserService;

@RestController
public class RegistrationController {

	@Autowired
	private AppUserService appUserService;

	@GetMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("register");
	}

	@GetMapping("/register/admin")
	public ModelAndView registerSuperUser() {
		return new ModelAndView("registerSuperUser");
	}

	@PostMapping("/auth/register")
	public ModelAndView register(@ModelAttribute("registerForm") RegisterationRequest request) {
		boolean status = appUserService.signUpUser(new AppUser(request.getFirstName(), request.getLastName(),
				request.getEmail(), request.getPassword(), AppUserRole.USER));

		if (!status) {
			return new ModelAndView("redirect:/register?error");
		}
		return new ModelAndView("redirect:/login");
	}

	@PostMapping("/auth/register/admin")
	public ModelAndView registerSuperUser(@ModelAttribute("registerForm") RegisterationRequest request) {
		boolean status = appUserService.signUpUser(new AppUser(request.getFirstName(), request.getLastName(),
				request.getEmail(), request.getPassword(), AppUserRole.ADMIN));

		if (!status) {
			return new ModelAndView("redirect:/register?error");
		}
		return new ModelAndView("redirect:/login");
	}

}
