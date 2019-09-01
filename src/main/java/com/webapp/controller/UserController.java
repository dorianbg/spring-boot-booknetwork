package com.webapp.controller;

import com.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("usersList", userService.findAll());
		model.addAttribute("crudOption","");
		return "listings/listUsers";
	}

	@GetMapping("/user/delete/{userName}")
	public String deleteUser(@PathVariable String userName, Model model, HttpSession session) {
		this.userService.deleteByName(userName);
		return "redirect:/users";
	}

}
