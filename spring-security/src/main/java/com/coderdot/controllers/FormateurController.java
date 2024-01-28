package com.coderdot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
//@RequestMapping("/api/formateurs")
@CrossOrigin(origins = "*")
public class FormateurController {

	@Autowired
	private FormateurService formateurService;
	
	@PostMapping("/inscrire")
	public ResponseEntity<Formateur> inscrireFormateur(@RequestBody Formateur formateur) {
     Formateur inscritFormateur = formateurService.inscrireFormateur(formateur);
     return new ResponseEntity<>(inscritFormateur, HttpStatus.CREATED);
	}
	 @GetMapping("/api/fuser/all")
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<List<Formateur>> getAllFormateurs() {
	        List<Formateur> allFormateurs = formateurService.getAllFormateurs();
	        return new ResponseEntity<>(allFormateurs, HttpStatus.OK);
	    }
	

	 	@PutMapping("/api/fuser/accepter/{formateurId}")
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<Formateur> accepterFormateur(@PathVariable Long formateurId) {
	        try {
	            Formateur formateurAccepte = formateurService.acceptFormateur(formateurId);
	            return new ResponseEntity<>(formateurAccepte, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace(); 
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	 	@DeleteMapping("/api/fuser/refuser/{formateurId}")
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<Void> refuserFormateur(@PathVariable Long formateurId) {
	        try {
	            formateurService.refuseFormateur(formateurId);
	            return new ResponseEntity<>(HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace(); 
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}

