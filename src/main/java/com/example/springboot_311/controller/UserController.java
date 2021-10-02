package com.example.springboot_311.controller;

import com.example.springboot_311.model.Role;
import com.example.springboot_311.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.example.springboot_311.service.RoleService;
import com.example.springboot_311.service.UserServiceImpl;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class UserController {
	private UserServiceImpl userService;
	private RoleService roleService;

	@Autowired
	public UserController(UserServiceImpl userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping(value = "/")
	public String helloPage() {
		return "redirect:/login";
	}

	@GetMapping(value = "/user")
	public String printWelcome(ModelMap model, Principal principal) {
		List<String> messages = new ArrayList<>();
		System.out.println(principal.getName());
		model.addAttribute("user", userService.findByEmail(principal.getName()));
		return "user";
	}

	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}


	@GetMapping(value = "/admin")
	public String printAllUsers(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("allUsers", userService.listUser());
		model.addAttribute("allRoles", roleService.getAllRoles());
		return "adminpage";
	}

	@PostMapping("admin/remove/{id}")
	public String removeUser(@PathVariable("id") Long id) {
		userService.removeUser(id);
		return "redirect:/admin";
	}

	@PostMapping(value = "/admin/add-user")
	public String addNewUser(@ModelAttribute("user") User user, @RequestParam(value = "myRoles", defaultValue = "ROLE_USER") String... myRole) {
		Set<Role> roles = Arrays.stream(myRole).map(s -> roleService.getRoleByName(s)).collect(Collectors.toSet());
		user.setRoles(roles);
		userService.addUser(user);
		return "redirect:/admin";
	}


	@PostMapping("/admin/edit/{id}")
	public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id, @RequestParam(value = "myRoles", defaultValue = "ROLE_USER") String... myRole) {
		Set<Role> roles = new HashSet<>();
		for (String s : myRole) {
			roles.add(roleService.getRoleByName(s));
		}
		user.setRoles(roles);
		userService.updateUser(user);
		return "redirect:/admin";
	}

}