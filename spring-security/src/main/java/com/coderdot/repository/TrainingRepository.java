package com.coderdot.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.coderdot.entities.Training;



@Repository
public interface TrainingRepository extends  JpaRepository<Training, Long>{

	
}
