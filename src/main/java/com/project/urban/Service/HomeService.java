package com.project.urban.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.urban.Entity.Home;

@Service
public interface HomeService {

    Home createHome(Home home);

    void deleteHome(Long homeId);

    Home editHome(Home home);

    List<Home> getAllHomes();
}
