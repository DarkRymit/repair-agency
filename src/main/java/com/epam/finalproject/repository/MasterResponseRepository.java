package com.epam.finalproject.repository;

import com.epam.finalproject.entity.MasterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterResponseRepository extends JpaRepository<MasterResponse, Long> {
    List<MasterResponse> findAllByMaster_Username(String username);
    List<MasterResponse> findAllByUser_Username(String username);
    Integer countAllByMaster_Username(String username);

}
