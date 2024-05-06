package com.project.urban.Controller;

import com.project.urban.DTO.EditUserDTO;
import com.project.urban.DTO.LoginDTO;
import com.project.urban.DTO.ResetPasswordDTO;
import com.project.urban.DTO.UserDTO;
import com.project.urban.Exception.ResourceNotFoundException;
import com.project.urban.Repository.UserRepository;
import com.project.urban.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/current-user")
	public ResponseEntity<UserDTO> getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String email = userDetails.getUsername();

			UserDTO userDTO = userService.getUserByEmail(email); // Sử dụng service để lấy thông tin người dùng từ
																	// username

			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
		}
	}

	@PostMapping("/")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		UserDTO savedUserDTO = userService.createUser(userDTO);
		return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
	}

	// build get user by id REST API
	// http://localhost:8081/api/users/1
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long userId) {
		UserDTO userDTO = userService.getUserById(userId);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	// Build Get All Users REST API
	// http://localhost:8081/api/users
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EditUserDTO> updateUser(@PathVariable("id") Long userId,
			@RequestBody EditUserDTO editUserDTO) {
		try {
			editUserDTO.setId(userId);
			EditUserDTO updatedUserDTO = userService.updateUser(editUserDTO);
			return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Build Delete User REST API
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<Object> loginUser(@RequestBody LoginDTO loginDto) {
		ResourceNotFoundException loginMesage = userService.loginUser(loginDto);
		return new ResponseEntity<Object>(loginMesage, HttpStatus.OK);
	}

	@PutMapping("/chagePassword/{id}")
	public ResponseEntity<ResetPasswordDTO> chagePasswordUser(@PathVariable("id") Long userId,
			@RequestBody ResetPasswordDTO resetPasswordDTO) {
		resetPasswordDTO.setId(userId);
		ResetPasswordDTO updatedUserDTO = userService.changResetPasswordUser(resetPasswordDTO);
		return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
	}
}
