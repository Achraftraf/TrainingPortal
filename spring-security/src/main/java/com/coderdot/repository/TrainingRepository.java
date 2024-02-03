package com.coderdot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coderdot.entities.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

//    List<Training> findByCategoryAndCity(String category, String city);

    List<Training> findByCategory(String category);

    List<Training> findByCity(String city);
    
    boolean existsBySchedule_DateAndIdNot(LocalDate date, Long id);

    List<Training> findByCategoryAndCity(String category, String city);
    @Query("SELECT t FROM Training t JOIN t.schedule s WHERE s.date = :date")
    List<Training> findByScheduleDate(@Param("date") LocalDate date);
}
