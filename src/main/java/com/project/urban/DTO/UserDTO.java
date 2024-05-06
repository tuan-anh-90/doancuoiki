package com.project.urban.DTO;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String password;
	private String email;
	private String name;
	private String role;
	private String chucVu;
	private String maNV;
	private String gioiTinh;
}
