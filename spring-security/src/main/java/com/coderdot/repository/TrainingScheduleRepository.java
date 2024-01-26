package com.coderdot.repository;

import com.coderdot.entities.TrainingSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, Long> {
    // Add any custom queries if needed
}