package com.coderdot.controllers;

import com.coderdot.entities.Entreprise;
import com.coderdot.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/entreprises")
public class EntrepriseController {

    @Autowired
    private EntrepriseService entrepriseService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entreprise> ajouterEntreprise(@RequestBody Entreprise entreprise) {
        try {
            Entreprise ajouterEntreprise = entrepriseService.addEntreprise(entreprise);
            return new ResponseEntity<>(ajouterEntreprise, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace(); // You can replace this with your logging mechanism
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Entreprise>> getAllEntreprises() {
        List<Entreprise> entreprises = entrepriseService.getAllEntreprises();
        return new ResponseEntity<>(entreprises, HttpStatus.OK);
    }

}
