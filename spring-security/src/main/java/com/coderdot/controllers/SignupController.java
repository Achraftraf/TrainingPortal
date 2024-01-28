package com.coderdot.controllers;

import com.coderdot.dto.SignupRequest;
import com.coderdot.entities.Customer;
import com.coderdot.services.AuthService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class SignupController {

	
	
    private final AuthService authService;

    @Autowired
    public SignupController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup") 
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        Customer createdCustomer = authService.createCustomer(signupRequest);
        if (createdCustomer != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create customer");
        }
    }
    
    @PostMapping("/admin/trainer") 
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> signupTrainer(@RequestBody SignupRequest signupRequest) {
    	 System.out.println("Received request to signup trainer");
        Customer createdTrainer = authService.createTrainer(signupRequest);
        if (createdTrainer != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainer);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create trainer");
        }
    }

    @GetMapping("/formateurs")
    public ResponseEntity<List<Customer>> getFormateurCustomers() {
        List<Customer> formateurCustomers = authService.getCustomersByRole(Customer.Role.ROLE_FORMATEUR);
        return ResponseEntity.ok(formateurCustomers);
    }

    
    @GetMapping("/formateurs/{id}")
    public ResponseEntity<?> getFormateurById(@PathVariable Long id) {
        Optional<Customer> formateur = authService.getFormateurById(id);

        if (formateur.isPresent()) {
            return ResponseEntity.ok(formateur.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Formateur not found");
        }
    }
}

