package com.project.urban.Service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.project.urban.DTO.EditUserDTO;
import com.project.urban.DTO.LoginDTO;
import com.project.urban.DTO.ResetPasswordDTO;
import com.project.urban.DTO.UserDTO;
import com.project.urban.Entity.User;
import com.project.urban.Exception.ResourceNotFoundException;

@Service
public interface UserService extends UserDetailsService {

	UserDTO createUser(UserDTO userDTO);

	UserDTO getUserById(Long userId);

	UserDTO getUserByUserName(String Email);

	UserDTO getUserByEmail(String userEmail);

	List<UserDTO> getAllUsers();

	EditUserDTO updateUser(EditUserDTO editUserDTO);

	void deleteUser(Long userId);

	ResourceNotFoundException loginUser(LoginDTO loginDTO);

	ResetPasswordDTO changResetPasswordUser(ResetPasswordDTO resetPasswordDTO);

	// reset password

	ResourceNotFoundException updateResetPasswordToken(String token, String email);

	User getByResetPasswordToken(String token);

	void updatePassword(String toKen, String newPassword);

}
