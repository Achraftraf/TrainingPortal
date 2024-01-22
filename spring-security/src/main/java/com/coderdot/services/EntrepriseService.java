package com.coderdot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderdot.entities.Entreprise;
import com.coderdot.repository.EntrepriseRepository;

@Service
public class EntrepriseService {

	@Autowired
	private EntrepriseRepository entrepriseRepository;
	
	public Entreprise addEntreprise(Entreprise entreprise) {
		
        return entrepriseRepository.save(entreprise);
    }

    public List<Entreprise> getAllEntreprises() {

        return entrepriseRepository.findAll();
    }

}
