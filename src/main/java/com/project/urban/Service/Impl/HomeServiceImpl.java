package com.project.urban.Service.Impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.urban.Entity.Home;
import com.project.urban.Repository.HomeRepository;
import com.project.urban.Service.HomeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeRepository homeRepository;

    @Override
    public Home createHome(Home home) {
        home.setTGBD(LocalDateTime.now());
        Home savedHome = homeRepository.save(home);
        System.out.println("Saved Note: " + savedHome);
        return savedHome;
    }

    @Override
    public void deleteHome(Long homeID) {
        Home họme = homeRepository.findById(homeID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "home not found"));
        homeRepository.deleteById(họme.getId());

    }

    @Override
    public List<Home> getAllHomes() {
        return homeRepository.findAll();
    }

    @Override
    public Home editHome(Home home) {
        // Tìm kiếm home dựa trên ID
        Home existingHome = homeRepository.findById(home.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Home not found"));

        // Cập nhật thông tin của existingHome với thông tin mới từ đối tượng home
        existingHome.setTieuDe(home.getTieuDe());
        existingHome.setNoiDung(home.getNoiDung());
        existingHome.setAnh(home.getAnh());
        // Các trường thông tin khác cần cập nhật

        // Lưu trữ thông tin đã cập nhật và trả về kết quả
        return homeRepository.save(existingHome);
    }

}
