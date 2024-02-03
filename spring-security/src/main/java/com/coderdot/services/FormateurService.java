package com.coderdot.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.coderdot.entities.Customer;
import com.coderdot.entities.Customer.Role;
import com.coderdot.entities.Formateur;
import com.coderdot.entities.Formateur.Status;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.FormateurRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class FormateurService {

	@Autowired
    private FormateurRepository formateurRepository;

	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private JavaMailSender javaMailSender;

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
        customer.setSkills(formateur.getSkils());
        customer.setPassword(formateur.getPassword());
        customer.setRoles(Collections.singleton(Role.ROLE_FORMATEUR)); 

        customerRepository.save(customer);
        formateurRepository.delete(formateur);

        return formateur;
    }


    public void refuseFormateur(Long formateurId) {
        Formateur formateur = formateurRepository.findById(formateurId)
                .orElseThrow(() -> new RuntimeException("Formateur not found"));

        formateur.setStatus(Status.REJECTED);

        formateurRepository.save(formateur);

        sendRejectionEmail(formateur.getEmail());
        formateurRepository.delete(formateur);
    }

    private void sendAcceptanceEmail(String formateurEmail) {
    	
    	try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(formateurEmail);
            helper.setSubject("Your Formateur Application has been Accepted");
            helper.setText("Dear Formateur, your application has been accepted. Welcome!");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private void sendRejectionEmail(String formateurEmail) {

       	SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(formateurEmail);
        message.setSubject("Your Formateur Application has been Rejected");
        message.setText("Dear Formateur, unfortunately, your application has been rejected.");

        javaMailSender.send(message);
    }
    
    public List<Formateur> getAllFormateurs() {
        return formateurRepository.findAll();
    }


}
