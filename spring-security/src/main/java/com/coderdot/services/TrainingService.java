package com.coderdot.services;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderdot.entities.Training;
import com.coderdot.repository.TrainingRepository;
import org.springframework.web.multipart.MultipartFile;
@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    public Training addTraining(Training training) {
        // Additional validation or business logic can be 	added here
        return trainingRepository.save(training);
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    
    public List<Training> getFilteredTrainings(String category, String city, LocalDate date) {
        // Implement logic to fetch trainings based on filters
        if (category != null && city != null && date != null) {
            // All parameters provided
            return trainingRepository.findByCategoryAndCityAndDate(category, city, date);
        } else if (category != null && city != null) {
            // Filter by category and city
            return trainingRepository.findByCategoryAndCity(category, city);
        } else if (category != null && date != null) {
            // Filter by category and date
            return trainingRepository.findByCategoryAndCity(category, date);
        } else if (city != null && date != null) {
            // Filter by city and date
            return trainingRepository.findByCityAndDate(city, date);
        } else if (category != null) {
            // Filter by category only
            return trainingRepository.findByCategory(category);
        } else if (city != null) {
            // Filter by city only
            return trainingRepository.findByCity(city);
        } else if (date != null) {
            // Filter by date only
            return trainingRepository.findByDate(date);
        } else {
            // No filters provided, return all
            return trainingRepository.findAll();
        }
    }




}

