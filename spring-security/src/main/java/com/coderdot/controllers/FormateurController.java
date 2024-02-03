package com.coderdot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderdot.entities.Formateur;
import com.coderdot.services.FormateurService;

@RestController
//@RequestMapping("/api/fuser")
@CrossOrigin(origins = "*")
public class FormateurController {

    @Autowired
    private FormateurService formateurService;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/inscrire")
    public ResponseEntity<Formateur> inscrireFormateur(@RequestBody Formateur formateur) {
        formateur.setPassword(passwordEncoder.encode(formateur.getPassword()));
        Formateur inscritFormateur = formateurService.inscrireFormateur(formateur);
        return ResponseEntity.ok(inscritFormateur);
    }

    @GetMapping("/api/fuser/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Formateur>> getAllFormateurs() {
        List<Formateur> allFormateurs = formateurService.getAllFormateurs();
        return ResponseEntity.ok(allFormateurs);
    }

    @PutMapping("/api/fuser/accepter/{formateurId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Formateur> accepterFormateur(@PathVariable Long formateurId) {
        try {
            Formateur formateurAccepte = formateurService.acceptFormateur(formateurId);
            return ResponseEntity.ok(formateurAccepte);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/fuser/refuser/{formateurId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> refuserFormateur(@PathVariable Long formateurId) {
        try {
            formateurService.refuseFormateur(formateurId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

