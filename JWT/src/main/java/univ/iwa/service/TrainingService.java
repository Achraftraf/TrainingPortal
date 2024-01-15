package univ.iwa.service;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import univ.iwa.model.Training;
import univ.iwa.repository.TrainingRepository;
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

    // Additional methods as needed
}

