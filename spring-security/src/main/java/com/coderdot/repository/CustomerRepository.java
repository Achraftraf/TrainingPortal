package com.coderdot.repository;

import com.coderdot.entities.Customer;
import com.coderdot.entities.Customer.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
    

    @Query("SELECT c FROM Customer c WHERE :role MEMBER OF c.roles")
    List<Customer> findTrainers(@Param("role") Role role);

	

	 @Query("SELECT c FROM Customer c WHERE c.id = :id AND :role MEMBER OF c.roles")
	    Optional<Customer> findByIdAndRoles(@Param("id") Long id, @Param("role") Customer.Role role);

}
