package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private Userrepo userRepository;

    public Users signUpUser(Users user) {
        if ("findamentor".equals(user.getRole())|| "becomeamentor".equals(user.getRole())) {
            user.setStatus("pending");
        } else {
            user.setStatus("approved");
        }
        return userRepository.save(user);
    }

    
    public List<Users> getBecomeaMentor() {
        return userRepository.findByRole("becomeamentor");
    }
    public List<Users> findamentor() {
        return userRepository.findByRole("findamentor");
    }

    public Users acceptBecomeaMentor(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if ("pending".equals(user.getStatus())) {
                user.setStatus("accepted");
                return userRepository.save(user);
            }
        }
        return null;
    }
    public Users acceptfindamentor(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if ("pending".equals(user.getStatus())) {
                user.setStatus("accepted");
                return userRepository.save(user);
            }
        }
        return null;
    }

    public boolean rejectBecomeaMentor(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }
        return false;
    }
    public boolean rejectfindamentor1(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }
        return false;
    }
    
    public String signIn(String email, String password, String role) {
        Users user = userRepository.findByEmail(email);

        if (user == null) {
            return "Invalid credentials"; // User does not exist
        }

        if (!user.getPassword().equals(password)) {
            return "Invalid credentials"; // Password mismatch
        }

        if (!user.getRole().equals(role)) {
            return "Invalid credentials"; // Role mismatch
        }

        if (user.getStatus().equals("pending")) {
            if ("admin".equals(role)) {
                return "Admin approval is pending"; // If the status is pending for Admin
            } else if ("findamentor".equals(role)) {
                return "Approval is pending"; // If the status is pending for Service provider
            } else if ("becomeamentor".equals(role)) {
                return "Approval is pending"; // If the status is pending for Resident
            }
        }

        if (user.getStatus().equals("accepted")) {
            switch (role) {
                case "admin":
                    return "admin";
                case "findamentor":
                    return "findamentor"; // For service provider
                case "becomeamentor":
                    return "becomeamentor"; // For resident
                default:
                    return "Invalid credentials"; // Catch-all for any other roles
            }
        }

        return "Invalid credentials"; // Catch-all for any other errors
    }


	public boolean rejectfindamentor(Long id) {
		Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }		return false;
	}


    
   
}
