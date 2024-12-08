package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Complaintrepo extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUsername(String username);

}
    