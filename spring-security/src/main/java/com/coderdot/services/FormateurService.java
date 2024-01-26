package com.coderdot.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderdot.entities.Customer;
import com.coderdot.entities.Customer.Role;
import com.coderdot.entities.Formateur;
import com.coderdot.entities.Formateur.Status;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.FormateurRepository;


@Service
public class FormateurService {

	@Autowired
    private FormateurRepository formateurRepository;

	@Autowired
    private CustomerRepository customerRepository;
	

    public Formateur inscrireFormateur(Formateur formateur) {
    	 if (formateur.getStatus() == null) {
             formateur.setStatus(Status.PENDING); 
         }
        return formateurRepository.save(formateur);
    }
    
    public Formateur acceptFormateur(Long formateurId) {
        Formateur formateur = formateurRepository.findById(formateurId)
                .orElseThrow(() -> new RuntimeException("Formateur not found"));

        formateur.setStatus(Status.ACCEPTED);

        formateurRepository.save(formateur);

        sendAcceptanceEmail(formateur.getEmail());

        Customer customer = new Customer();
        customer.setEmail(formateur.getEmail());
        customer.setName(formateur.getName());
        customer.setPassword(formateur.getPassword());
        customer.setRoles(Collections.singleton(Role.ROLE_FORMATEUR)); 

        customerRepository.save(customer);

//        formateurRepository.delete(formateur);

        return formateur;
    }


    public void refuseFormateur(Long formateurId) {
        Formateur formateur = formateurRepository.findById(formateurId)
                .orElseThrow(() -> new RuntimeException("Formateur not found"));

        formateur.setStatus(Status.REJECTED);

        formateurRepository.save(formateur);

        sendRejectionEmail(formateur.getEmail());
    }

    private void sendAcceptanceEmail(String formateurEmail) {
    }
    private void sendRejectionEmail(String formateurEmail) {

    }
    
    public List<Formateur> getAllFormateurs() {
        return formateurRepository.findAll();
    }


}
