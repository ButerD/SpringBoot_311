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

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		List<String> messages = new ArrayList<>();
		System.out.println(principal.getName());
		model.addAttribute("user", userService.findByEmail(principal.getName()));
		return "user";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "/login.html";
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

	@GetMapping(value = "admin/new")
	public String newUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles",roleService.getAllRoles());
		return "/trash/addUser.html";
	}

	@PostMapping(value = "/admin/add-user")
	public String addNewUser(@ModelAttribute("user") User user, @RequestParam(value = "myRoles", defaultValue = "ROLE_USER") String... myRole) {
//		Set<Role> roles = new HashSet<>();
//		for (String s : myRole) {
//			roles.add(roleService.getRoleByName(s));
//		}
		Set<Role> roles = new HashSet<>(Arrays.stream(myRole).map(s -> roleService.getRoleByName(s)).collect(Collectors.toList())) ;
		user.setRoles(roles);
		userService.addUser(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "/{id}")
	public String showUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute(userService.getUser(id));
		return "/trash/userinfo.html";
	}

	@GetMapping(value = "/admin/{id}/edit")
	public  String editUser(Model model, @PathVariable("id") Long id) {
		model.addAttribute("user", userService.getUser(id) );
		model.addAttribute("roles",roleService.getAllRoles());
		return "/trash/edit.html";
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