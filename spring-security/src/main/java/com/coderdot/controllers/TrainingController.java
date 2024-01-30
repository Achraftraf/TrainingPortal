package com.coderdot.controllers;


import com.coderdot.entities.Customer;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.EntrepriseRepository;
import com.coderdot.repository.TrainingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coderdot.entities.Training;
import com.coderdot.entities.TrainingParticipant;
import com.coderdot.entities.TrainingSchedule;
import com.coderdot.services.TrainingService;
import com.coderdot.entities.Entreprise;
import com.coderdot.entities.Training;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private CustomerRepository customerRepository;

    
    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ASSISTANT')")
    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> allTrainings = trainingService.getAllTrainings();
        return new ResponseEntity<>(allTrainings, HttpStatus.OK);
    }    

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ASSISTANT')")
    public ResponseEntity<?> getTrainingById(@PathVariable Long id) {
        Optional<Training> training = trainingService.getTrainingById(id);

        if (training.isPresent()) {
            return ResponseEntity.ok(training.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training not found");
        }
    }
    
    @GetMapping("/schedule/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ASSISTANT')")
    public ResponseEntity<?> getTrainingByTrainingScheduleId(@PathVariable Long id) {
        Optional<Training> training = trainingService.getTrainingByTrainingScheduleId(id);

        if (training.isPresent()) {
            return ResponseEntity.ok(training.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training not found for the given TrainingSchedule ID");
        }
    }
    
//    @GetMapping("/filtered")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<Training>> getFilteredTrainings(
//        @RequestParam(required = false) String category,
//        @RequestParam(required = false) String city,
//        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        List<Training> filteredTrainings = trainingService.getFilteredTrainings(category, city, date);
//        return new ResponseEntity<>(filteredTrainings, HttpStatus.OK);
//    }

//    @PostMapping("/schedule")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<String> scheduleTraining(@RequestBody TrainingSchedule trainingSchedule) {
//        try {
//            LocalDate date = trainingSchedule.getDate();
////            Long trainerId = trainingSchedule.getTrainer().getId();
//            Long trainerId = (trainingSchedule.getTrainer() != null) ? trainingSchedule.getTrainer().getId() : null;
//
////            Long enterpriseId = trainingSchedule.getEnterprise().getId();
//            Long enterpriseId = (trainingSchedule.getEnterprise() != null) ? trainingSchedule.getEnterprise().getId() : null;
//
//
//            // Additional validation or business logic can be added here
//
//            // Fetch the trainer and enterprise from the repository based on provided IDs
//            Customer trainer = customerRepository.findById(trainerId)
//                    .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
//            Entreprise enterprise = entrepriseRepository.findById(enterpriseId)
//                    .orElseThrow(() -> new IllegalArgumentException("Enterprise not found"));
//
//            // Set the necessary information for scheduling
//            trainingSchedule.setDate(date);
//            trainingSchedule.setTrainer(trainer);
//            trainingSchedule.setEnterprise(enterprise);
//
//            // Call the service method to schedule the training
//            trainingService.scheduleTraining(trainingSchedule);
//
//            return ResponseEntity.ok("Training scheduled successfully");
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception details
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    
    
//    @PostMapping("/schedule")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<String> scheduleTraining(
//            @RequestBody TrainingSchedule trainingSchedule,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//            @RequestParam Long trainerId,
//            @RequestParam Long enterpriseId) {
//        try {
//            // Fetch the trainer and enterprise from the repository based on provided IDs
//            Customer trainer = customerRepository.findById(trainerId)
//                    .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
//            Entreprise enterprise = entrepriseRepository.findById(enterpriseId)
//                    .orElseThrow(() -> new IllegalArgumentException("Enterprise not found"));
//
//            // Fetch an existing Training from the database (you might want to modify this based on your actual logic)
//            // For example, if you have a TrainingRepository, you can fetch it like this:
//            Training existingTraining = trainingRepository.findById(trainingSchedule.getTraining().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("Training not found"));
//
//            // Set the necessary information for scheduling
//            trainingSchedule.setDate(date);
//            trainingSchedule.setTrainer(trainer);
//            trainingSchedule.setEnterprise(enterprise);
//            trainingSchedule.setTraining(existingTraining); // Set the existing training
//
//            // Call the service method to schedule the training
//            trainingService.scheduleTraining(trainingSchedule);
//
//            return ResponseEntity.ok("Training scheduled successfully");
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception details
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//

    @GetMapping("/message")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }
    
    
    @PutMapping("/{id}/register")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> registerForTraining(@PathVariable Long id, @RequestBody TrainingParticipant participant) {
        try {
            Optional<Training> optionalTraining = trainingService.getTrainingById(id);
            if (optionalTraining.isPresent()) {
                Training training = optionalTraining.get();
                List<TrainingParticipant> participants = training.getParticipants();
                participants.add(participant);
                training.setParticipants(participants);
                trainingRepository.save(training);
                return ResponseEntity.ok("Successfully registered for training");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Additional endpoints as needed
}
