package com.mitrais.rms.control;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mitrais.rms.model.User;
import com.mitrais.rms.service.UserService;

@Controller
public class UserController {

	// Messages
	private static final String REGISTER_FALSE = "Sorry! Register unsuccessfully";
	private static final String REGISTER_SUCCESS = "Register successfully";
	private static final String UPDATE_SUCCESS = "Update successfully";
	private static final String USER_EXISTS = "Username is already used";
	private static final String CANT_FIND_ID = "Can't find result with this ID";

	@Autowired
	private UserService userService;

	// Access to Home Page
	@GetMapping("/")
	public ModelAndView goHome() {
		return new ModelAndView("index");
	}

	// Access to Register Page & Register
	@GetMapping("/register")
	public ModelAndView goRegister() {
		User user = new User();
		ModelAndView modelAndView = new ModelAndView("register");
		modelAndView.addObject("user", user);
		return modelAndView;
	}
	
	@PostMapping("/register")
	public ModelAndView register(@ModelAttribute("user") User user) {
		ModelAndView modelAndView = new ModelAndView("register");
		User userExists = userService.findUserByUsername(user.getUsername());
		if (userExists == null) {
			try {
				user.setUsername(user.getUsername());
				user.setPassword(user.getPassword());
				userService.saveUser(user);
				modelAndView.addObject("successMessage", REGISTER_SUCCESS);
			} catch (Exception e) {
				modelAndView.addObject("errorMessage", REGISTER_FALSE);

			}
		} else {
			modelAndView.addObject("errorMessage", USER_EXISTS);
		}
		return modelAndView;
	}

	// Access to Login Page
	@GetMapping("/login")
	public ModelAndView goLogin() {
		return new ModelAndView("login");
	}

	// Access to Add User Page & Add User
	@GetMapping("/addUser")
	public ModelAndView goAddUser() {
		User user = new User();
		ModelAndView modelAndView = new ModelAndView("add_user");
		modelAndView.addObject("user", user);
		return modelAndView;
	}
	
	@PostMapping("/addUser")
	public ModelAndView addUser(@ModelAttribute("user") User user) {
		ModelAndView modelAndView = new ModelAndView("add_user");
		User userExists = userService.findUserByUsername(user.getUsername());
		if (userExists == null) {
			try {
				user.setUsername(user.getUsername());
				user.setPassword(user.getPassword());
				userService.saveUser(user);
				modelAndView.addObject("successMessage", REGISTER_SUCCESS);
			} catch (Exception e) {
				modelAndView.addObject("errorMessage", REGISTER_FALSE);

			}
		} else {
			modelAndView.addObject("errorMessage", USER_EXISTS);
		}
		return modelAndView;
	}

	// Access to User Info Page
	@GetMapping("/loginSuccess")
	public ModelAndView goLoginSuccess(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("login_success");
		String username = principal.getName();
		modelAndView.addObject("username", username);
		return modelAndView;
	}

	// Access to List User Page
	@GetMapping("/userList")
	public ModelAndView goUserList() {
		List<User> userList = userService.getAllUsers();
		ModelAndView modelAndView = new ModelAndView("user_list");
		modelAndView.addObject("users", userList);
		return modelAndView;
	}
	
	// Delete User
	@GetMapping("/deleteUser")
	public ModelAndView deleteUser(@PathParam(value = "id") Long id) {
		ModelAndView view = new ModelAndView("redirect:/userList");
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			return view;
		}
	
		return view;
	}
	

	// Access to update User page and update User
	@GetMapping("/updateUser")
	public ModelAndView goUpdateUser(@PathParam(value = "id") Long id) {
		User user = userService.findUserById(id);
		ModelAndView view = new ModelAndView("edit_user");
		view.addObject("user", user);
		return view;
	}
	
	@PostMapping("/updateUser")
	public ModelAndView updateUser(@ModelAttribute("user") User user) {
		ModelAndView view = new ModelAndView("edit_user");
			User userUpdate = userService.findUserById(user.getId());
			try {
				userUpdate.setPassword(user.getPassword());
				view.addObject("successMessage",UPDATE_SUCCESS);
				userService.saveUser(userUpdate);
			} catch (Exception e) {
				return view;
			}
		
		return view;
	}
	
	//Find user by ID
	@PostMapping("/getUserById")
	public ModelAndView getUserById(@ModelAttribute(value = "id") Long id) {
		ModelAndView view = new ModelAndView("user_list");
		try {
			User user = userService.findUserById(id);
				view.addObject("userFound", user);
			} catch (Exception e) {
				view.addObject("errorMessage", CANT_FIND_ID);
				return view;
			}
		return view;
	}
}
