package com.project.urban.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.urban.DTO.DonDTO;
import com.project.urban.Entity.Don;
import com.project.urban.Service.DonService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/dons")
public class DonController {

    @Autowired
    private DonService donService;

    @PostMapping("/")
    public ResponseEntity<?> createDon(@RequestBody Don donDTO) {
        Don savedDonDTO = donService.createDon(donDTO);

        return new ResponseEntity<>(savedDonDTO, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Don>> getAllDon() {
        List<Don> dons = donService.getAllDons();
        return new ResponseEntity<>(dons, HttpStatus.OK);
    }

    @GetMapping("/allByEmail")
    public Iterable<Don> getAllByUserEmail(@RequestParam String email) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return donService.getByEmail(userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDon(@PathVariable("id") Long donId) {
        donService.deleteDon(donId);
        return new ResponseEntity<>("Don successfully deleted!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonDTO> updateDon(@PathVariable("id") Long donId,
            @RequestBody DonDTO donDTO) {
        try {
            donDTO.setId(donId);
            DonDTO updateDonDTO = donService.updateDon(donDTO);
            return new ResponseEntity<>(updateDonDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}