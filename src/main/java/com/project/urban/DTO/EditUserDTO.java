package com.project.urban.DTO;

import lombok.Data;

@Data
public class EditUserDTO {
    private Long id;
    private String email;
    private String name;
    private String chucVu;
    private String gioiTinh;
}
