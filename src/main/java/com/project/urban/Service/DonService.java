package com.project.urban.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.urban.DTO.DonDTO;
import com.project.urban.Entity.Don;

@Service
public interface DonService {

    Don createDon(Don don);

    void deleteDon(Long donId);

    List<Don> getAllDons();

    Iterable<Don> getByEmail(String email);

    DonDTO updateDon(DonDTO donDTO);
}