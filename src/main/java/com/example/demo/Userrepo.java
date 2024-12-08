package com.example.demo;


import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Userrepo extends JpaRepository<Users, Long> {
    List<Users> findByRole(String role);
    Users findByEmail(String email);

}
