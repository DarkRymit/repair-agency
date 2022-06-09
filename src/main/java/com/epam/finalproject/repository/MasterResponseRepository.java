package com.epam.finalproject.repository;

import com.epam.finalproject.entity.MasterResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterResponseRepository extends JpaRepository<MasterResponse, Long> {
    List<MasterResponse> findAllByMaster_Username(String username);
    List<MasterResponse> findAllByUser_Username(String username);
    Integer countAllByMaster_Username(String username);

}
