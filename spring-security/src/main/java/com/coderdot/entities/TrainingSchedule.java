package com.coderdot.entities;

import java.time.LocalDate;


import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

//TrainingSchedule.java
@Entity
public class TrainingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne	
    @JoinColumn(name = "training_id")
    private Training training;
    
    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Where(clause = "trainer_id IN (SELECT c.id FROM customer c JOIN customer_roles cr ON c.id = cr.id WHERE cr.roles = 'ROLE_FORMATEUR')")
    private Customer trainer;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Entreprise enterprise;


    private LocalDate date;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	 
	

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Customer getTrainer() {
        return trainer;
    }

    public void setTrainer(Customer trainer) {
        this.trainer = trainer;
    }

    public Entreprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Entreprise enterprise) {
        this.enterprise = enterprise;
    }

 

    // Constructors, getters, setters, etc.
}