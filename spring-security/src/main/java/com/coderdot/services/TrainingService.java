package com.coderdot.services;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.coderdot.entities.Customer;
import com.coderdot.entities.Entreprise;
import com.coderdot.entities.Training;
import com.coderdot.entities.TrainingSchedule;
import com.coderdot.repository.TrainingRepository;
import com.coderdot.repository.TrainingScheduleRepository;

import jakarta.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;
@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;
    
    @Autowired
    private TrainingScheduleRepository scheduleRepository; 

    public Training addTraining(Training training) {
        // Additional validation or business logic can be 	added here
        return trainingRepository.save(training);
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public void scheduleTraining(TrainingSchedule trainingSchedule) {
        try {
            // Additional validation or business logic can be added here
            // Ensure the relationship between Training and TrainingSchedule is set
            Training training = trainingSchedule.getTraining();
            if (training != null) {
                training.setSchedule(trainingSchedule);
                trainingRepository.save(training);
                // Log success message
                System.out.println("Training scheduled successfully");
            } else {
                throw new IllegalArgumentException("Training not provided in TrainingSchedule");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            throw e; // Rethrow the exception for debugging
        }
    }
//    
//    public List<Training> getFilteredTrainings(String category, String city, LocalDate date) {
//        // Implement logic to fetch trainings based on filters
//        if (category != null && city != null && date != null) {
//            // All parameters provided
//            return trainingRepository.findByCategoryAndCityAndDate(category, city, date);
//        } else if (category != null && city != null) {
//            // Filter by category and city
//            return trainingRepository.findByCategoryAndCity(category, city);
//        } else if (category != null && date != null) {
//            // Filter by category and date
//            return trainingRepository.findByCategoryAndCity(category, date);
//        } else if (city != null && date != null) {
//            // Filter by city and date
//            return trainingRepository.findByCityAndDate(city, date);
//        } else if (category != null) {
//            // Filter by category only
//            return trainingRepository.findByCategory(category);
//        } else if (city != null) {
//            // Filter by city only
//            return trainingRepository.findByCity(city);
//        } else if (date != null) {
//            // Filter by date only
//            return trainingRepository.findByDate(date);
//        } else {
//            // No filters provided, return all
//            return trainingRepository.findAll();
//        }
//    }


    public TrainingSchedule scheduleTraining(
            Training training,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Customer trainer,
            Entreprise entreprise) {
        try {
            // Additional validation or business logic can be added here

            if (training == null) {
                throw new IllegalArgumentException("Training cannot be null");
            }

            if (date == null) {
                throw new IllegalArgumentException("Date cannot be null");
            }

            if (trainer == null) {
                throw new IllegalArgumentException("Trainer cannot be null");
            }

            if (entreprise == null) {
                throw new IllegalArgumentException("Enterprise cannot be null");
            }

            TrainingSchedule trainingSchedule = new TrainingSchedule();
            trainingSchedule.setTraining(training);
            trainingSchedule.setDate(date);
            trainingSchedule.setTrainer(trainer);
            trainingSchedule.setEnterprise(entreprise);

            return scheduleRepository.save(trainingSchedule);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            throw e; // Rethrow the exception for debugging
        }
    }


    private final TrainingScheduleRepository trainingScheduleRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, TrainingScheduleRepository trainingScheduleRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingScheduleRepository = trainingScheduleRepository;
    }

    @Transactional
    public void saveTraining(Training training) {
        // create or retrieve the associated TrainingSchedule
        TrainingSchedule schedule = training.getSchedule();

        // set the TrainingSchedule in the Training entity
        training.setSchedule(schedule);

        // save TrainingSchedule first
        trainingScheduleRepository.save(schedule);

        // then save Training
        trainingRepository.save(training);
    }

}

