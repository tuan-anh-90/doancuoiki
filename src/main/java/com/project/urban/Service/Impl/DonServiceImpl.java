package com.project.urban.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.urban.DTO.DonDTO;
import com.project.urban.Entity.Don;
import com.project.urban.Entity.User;
import com.project.urban.Repository.DonRepository;
import com.project.urban.Repository.UserRepository;
import com.project.urban.Service.DonService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DonServiceImpl implements DonService {

    @Autowired
    private DonRepository donRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Don createDon(Don don) {
        // Thực hiện xác nhận thông tin người dùng (nếu cần)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            Optional<User> userOptional = userRepository.findByEmail(username);

            if (userOptional.isPresent()) {
                // Thiết lập thông tin người dùng cho Note
                don.setUser(userOptional.get());

                // Lưu đối tượng Note vào cơ sở dữ liệu
                Don savedDon = donRepository.save(don);

                System.out.println("Saved Note: " + savedDon);

                return savedDon;
            } else {
                // Xử lý khi không tìm thấy người dùng trong cơ sở dữ liệu
                throw new RuntimeException("User not found in the database");
            }
        } else {
            // Xử lý khi không có thông tin người dùng hoặc không phải là UserDetails
            throw new RuntimeException("User not authenticated or UserDetails not found");
        }
    }

    @Override
    public void deleteDon(Long donID) {
        Don don = donRepository.findById(donID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "don not found"));
        donRepository.deleteById(don.getId());

    }

    @Override
    public DonDTO updateDon(DonDTO donDTO) {
        Don existingUser = donRepository.findById(donDTO.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Don not found"));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(donDTO, existingUser);

        Don updatedDon = donRepository.save(existingUser);

        DonDTO updatedDonDTO = modelMapper.map(updatedDon, DonDTO.class);
        return updatedDonDTO;
    }

    @Override
    public List<Don> getAllDons() {
        return donRepository.findAll();
    }

    @Override
    public Iterable<Don> getByEmail(String email) {
        return donRepository.findAllByUserEmail(email);
    }
}