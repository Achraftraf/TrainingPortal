package univ.iwa.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import univ.iwa.model.Training;



@Repository
public interface TrainingRepository extends  JpaRepository<Training, Long>{

	
}
