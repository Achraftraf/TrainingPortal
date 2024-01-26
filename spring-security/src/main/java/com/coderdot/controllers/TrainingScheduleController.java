package com.coderdot.controllers;



import com.coderdot.entities.TrainingSchedule;

import com.coderdot.services.TrainingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/training-schedules")
public class TrainingScheduleController {

    @Autowired
    private TrainingService trainingService;

    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> scheduleTraining(@RequestBody TrainingSchedule trainingSchedule) {
        try {
            // Log the received training schedule
            System.out.println("Received Training Schedule: " + trainingSchedule);

            // Call the service method to schedule the training
            trainingService.scheduleTraining(trainingSchedule);

            return ResponseEntity.ok("Training scheduled successfully");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Additional methods if needed...
}
    // Additional endpoints for training schedules as needed
