package com.coderdot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderdot.entities.TrainingParticipant;

@Repository
public interface TrainingParticipantRepository extends JpaRepository<TrainingParticipant, Long> {
    // You can add custom query methods if needed
}
