package com.coderdot.entities;

import java.time.LocalDate;


import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class TrainingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Customer trainer;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Entreprise enterprise;

    private LocalDate date;
    
    @JsonBackReference
    @ManyToOne // Assuming a Many-to-One relationship with Training
    @JoinColumn(name = "training_id") // Adjust the column name based on your actual database schema
    private Training training;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    
    public void setTraining(Training training) {
        this.training = training;
    }
    
    public Training getTraining() {
        return training;
    }

}

    // Constructors, getters, setters, etc.
