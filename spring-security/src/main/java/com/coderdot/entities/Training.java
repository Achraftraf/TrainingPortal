package com.coderdot.entities;



import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data

@Entity
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int hours;
    private BigDecimal cost;
    private String objectives;
    private String detailedProgram;
    
    private String category;
    private String city;
//    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Entreprise enterprise;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "training")
    private TrainingSchedule schedule;

}

