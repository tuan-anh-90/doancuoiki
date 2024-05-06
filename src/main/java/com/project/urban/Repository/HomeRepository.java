package com.project.urban.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.urban.Entity.Home;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {

}
