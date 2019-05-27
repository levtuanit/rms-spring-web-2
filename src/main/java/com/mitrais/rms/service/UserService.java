package com.mitrais.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mitrais.rms.model.User;
import com.mitrais.rms.repo.UserRepository;

@Service("userService")
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public User findUserById(Long id) {
		return userRepository.findById(id).get();
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User checkLogin(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}
}
