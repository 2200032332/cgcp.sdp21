package com.example.demo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:3000") // Adjust to your frontend origin

public class ComplaintController {

    @Autowired
    private Complaintservice complaintService; 
    
    
    @Autowired
    private Complaintrepo complaintrepo; 

    @PostMapping
    public String postComplaint(
        @RequestParam String category,
        @RequestParam String description,
        @RequestParam String urgency,
        @RequestParam String location,
        @RequestParam MultipartFile attachment,
        @RequestParam String username,
        @RequestParam String email
    ) {
        complaintService.saveComplaint(category, description, urgency, location, attachment, username, email);
        return "Complaint posted successfully!";
    }
    
    @GetMapping
    public ResponseEntity<List<Complaint>> getComplaintsByUsername(@RequestParam String username) {
        List<Complaint> complaints = complaintrepo.findByUsername(username);
        for (Complaint complaint : complaints) {
            complaint.setAttachmentBase64(complaint.getAttachmentAsBase64());
        }

        return ResponseEntity.ok(complaints);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintrepo.findAll();
        
        for (Complaint complaint : complaints) {
            complaint.setAttachmentBase64(complaint.getAttachmentAsBase64());
        }

        return ResponseEntity.ok(complaints);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Complaint> updateComplaintStatus(
            @PathVariable Long id,
            @RequestBody Complaint complaint) {
        // You can choose to set the status directly from the frontend or via the body request
        Complaint updatedComplaint = complaintService.updateStatus(id, "In Progress");
        return ResponseEntity.ok(updatedComplaint);
    }


    
    
    

}
 