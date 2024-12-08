package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")  // Allow requests from the frontend URL
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private Userrepo  userRepository ;
    // Endpoint to handle user signup
    @PostMapping("/signup")
    public ResponseEntity<Users> signUp(@RequestBody Users user) {
        Users savedUser = userService.signUpUser(user);
        return ResponseEntity.ok(savedUser);
    }
     
    @GetMapping("/serviceProviders")
    public ResponseEntity<List<Users>> getAllBecomeaMentor() {
        List<Users> serviceProviders = userService.getBecomeaMentor();
        return ResponseEntity.ok(serviceProviders);
    }
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getfindamentor() {
        List<Users> serviceProviders = userService.findamentor();
        return ResponseEntity.ok(serviceProviders);
    }
    
    

    // Endpoint to accept a service provider's role (change status to accepted)
    @PutMapping("/BecomeaMentor/{id}/accept")
    public ResponseEntity<Users> acceptBecomeaMentor(@PathVariable Long id) {
        Users updatedUser = userService.acceptBecomeaMentor(id);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/findamentor/{id}/accept")
    public ResponseEntity<Users> acceptresident(@PathVariable Long id) {
        Users updatedUser = userService.acceptfindamentor(id);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Endpoint to reject a service provider's role (delete user)
    @DeleteMapping("/BecomeaMentor/{id}/reject")
    public ResponseEntity<Void> rejectBecomeaMentor(@PathVariable Long id) {
        boolean isDeleted = userService.rejectBecomeaMentor(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/findamentor/{id}/reject")
    public ResponseEntity<Void> rejectfindamentor(@PathVariable Long id) {
        boolean isDeleted = userService.rejectfindamentor(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/signin")
    public String signIn(@RequestParam String email, @RequestParam String password, @RequestParam String role) {
        return userService.signIn(email, password, role);
    }
    @GetMapping("/username/{email}")
    public String getUsernameByEmail(@PathVariable String email) {
        try {
            // Fetch user by email using the repository method findByEmail
            Users user = userRepository.findByEmail(email); 

            if (user != null) {
                return user.getName();  // Return the user's name if user is found
            } else {
                return "User not found"; // Return a message if the user is not found
            }
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error occurred: " + e.getMessage());
            return "Internal server error. Please try again later."; // Graceful error message
        }
    }

    }


