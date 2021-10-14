package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class Usercontroller {

	@Autowired
	private UserRepository userRepository;

	// Get all users method
	@GetMapping
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
//Get user by id 

	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id") long userId) {

		return this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
	}

//Create users
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);

	}

	// update users
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
		User existingUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user not found with id :" + userId));

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		return this.userRepository.save(existingUser);

	}

// Delete users by id

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long userId) {
		User existingUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		this.userRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}

}
