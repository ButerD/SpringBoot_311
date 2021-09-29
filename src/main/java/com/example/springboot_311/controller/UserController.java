package com.example.springboot_311.controller;

import com.example.springboot_311.model.Role;
import com.example.springboot_311.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
		return "index";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		List<String> messages = new ArrayList<>();
		model.addAttribute("user", userService.findByUserName(principal.getName()));
		return "user/hello";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}


	@GetMapping(value = "/admin")
	public String printAllUsers(Model model) {
		model.addAttribute("userList", userService.listUser());
		return "users";
	}

	@GetMapping("/remove/{id}")
	public String removeUser(@PathVariable("id") Long id) {
		userService.removeUser(id);
		return "redirect:/admin";
	}

	@GetMapping(value = "new")
	public String newUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles",roleService.getAllRoles());
		return "addUser";
	}

	@PostMapping
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
		return "userinfo";
	}

	@GetMapping(value = "/{id}/edit")
	public  String editUser(Model model, @PathVariable("id") Long id) {
		model.addAttribute("user", userService.getUser(id) );
		model.addAttribute("roles",roleService.getAllRoles());
		return "edit";
	}

	@PostMapping("/{id}")
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