package com.project.urban.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.project.urban.Entity.Don;

import java.util.List;

@Repository
public interface DonRepository extends JpaRepository<Don, Long> {

    @Query("from Don d where d.user.email = :email")
    List<Don> findAllByUserEmail(String email);
}
