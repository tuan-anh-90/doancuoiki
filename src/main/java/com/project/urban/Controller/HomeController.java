package com.project.urban.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.urban.Entity.Home;
import com.project.urban.Service.HomeService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/homes")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PostMapping("/")
    public ResponseEntity<?> createHome(@RequestBody Home home) {
        Home savedHome = homeService.createHome(home);

        return new ResponseEntity<>(savedHome, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Home>> getAllHome() {
        List<Home> homes = homeService.getAllHomes();
        return new ResponseEntity<>(homes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHome(@PathVariable("id") Long homeId) {
        homeService.deleteHome(homeId);
        return new ResponseEntity<>("Home successfully deleted!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public Home editHome(@PathVariable Long id, @RequestBody Home home) {
        // Set ID cho home object dựa trên ID từ path variable
        home.setId(id);

        // Gọi phương thức editHome từ service để xử lý việc cập nhật thông tin Home
        return homeService.editHome(home);
    }
}