package com.webproj.kirakira.controller;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.simple.JSONObject;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webproj.kirakira.exceptions.ResourceNotFoundException;
import com.webproj.kirakira.model.User;
import com.webproj.kirakira.repository.UserRepository;

@RestController
@RequestMapping("/kira/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get all users list.
	 *
	 * @return the list
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

	/**
	 * Gets users by id.
	 *
	 * @param userId the user id
	 * @return the users by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUsersById(@PathVariable(value = "id") UUID userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
		return ResponseEntity.ok().body(user);
	}

	/**
	 * Create user user.
	 *
	 * @param user the user
	 * @return the user
	 */

	@PostMapping("/signup")
	public Map createUser(@Validated @Valid @RequestBody User user) {

		Map<String, String> response = new HashMap<>();

		if (null != userRepository.findByphoneNumber(user.getPhoneNumber())) {

			response.put("status", "error");
			response.put("message", "Phone number already exist");
			response.put("errorId", "phone number");
			return response;

		} else {
			userRepository.save(user);
			response.put("status", "success");
			response.put("message", "user added successful");

			return response;
		}
	}

	/**
	 * Update user response entity.
	 *
	 * @param userId      the user id
	 * @param userDetails the user details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */

	@PutMapping("/users/{id}")
	public Map updateUser(@PathVariable(value = "id") UUID userId, @Validated @RequestBody User userDetails,
			HttpServletRequest request) throws ResourceNotFoundException {
		JSONObject response = new JSONObject();
		if (!userRepository.existsById(userId)) {

			response.put("status", "error");
			response.put("message", "user not found");

			return response;

		} else {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));

			user.setName(userDetails.getName());
			user.setAddress(userDetails.getAddress());
			user.setUpdatedAt(new Date());
			user.setBusinessName(userDetails.getBusinessName());
			user.setEmail(userDetails.getEmail());
			user.setJobPosition(userDetails.getJobPosition());
			user.setNotes(userDetails.getNotes());
			user.setWebsite(userDetails.getWebsite());

			User updatedUser = userRepository.save(user);
			ResponseEntity.ok(updatedUser);

			response.put("status", "success");
			response.put("message", "user updated successful");
			response.put("data", updatedUser);
		}
		return response;
	}

	/**
	 * Delete user map.
	 *
	 * @param userId the user id
	 * @return the map
	 * @throws Exception the exception
	 */

	@DeleteMapping("/user/{id}")
	public Map<String, String> deleteUser(@PathVariable(value = "id") UUID userId) throws Exception {
		Map<String, String> response = new HashMap<>();
		if (!userRepository.existsById(userId)) {

			response.put("status", "error");
			response.put("message", "user not found");

		} else {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
			userRepository.delete(user);

			response.put("status", "success");
			response.put("message", "user deleted successful");
		}
		return response;
	}

}
