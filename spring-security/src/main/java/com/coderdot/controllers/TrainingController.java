package com.coderdot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderdot.entities.Training;
import com.coderdot.services.TrainingService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Training> addTraining(@RequestBody Training training) {
        try {
            // No need to manually check role here; @PreAuthorize handles it
            Training addedTraining = trainingService.addTraining(training);
            return new ResponseEntity<>(addedTraining, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace(); // You can replace this with your logging mechanism
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')") // Adjust role based on your use case
    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> allTrainings = trainingService.getAllTrainings();
        return new ResponseEntity<>(allTrainings, HttpStatus.OK);
    }    

    @GetMapping("/message")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }

    // Additional endpoints as needed
}
