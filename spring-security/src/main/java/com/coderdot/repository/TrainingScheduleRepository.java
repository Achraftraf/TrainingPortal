package com.coderdot.repository;

import com.coderdot.entities.Training;
import com.coderdot.entities.TrainingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, Long> {

    Optional<TrainingSchedule> findByTraining(Training training);
    Optional<TrainingSchedule> findByTraining_Id(Long trainingId);
}
