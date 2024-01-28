package com.coderdot.services;

import com.coderdot.dto.SignupRequest;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Customer.Role;
import com.coderdot.repository.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer createCustomer(SignupRequest signupRequest) {
        // Check if customer already exists
        if (customerRepository.existsByEmail(signupRequest.getEmail())) {
            return null;
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(signupRequest, customer);

        // Set default role to ROLE_USER if no roles are provided
        Set<Role> roles = signupRequest.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = Collections.singleton(Role.ROLE_USER);
        }
        customer.setRoles(roles);

        // Hash the password before saving
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        customer.setPassword(hashPassword);

        // Save the customer to the repository
        Customer createdCustomer = customerRepository.save(customer);

        // Set the ID and return the created customer
        createdCustomer.setId(createdCustomer.getId());
        return createdCustomer;
    }

    @Override
    public Customer createTrainer(SignupRequest signupRequest) {
    	 System.out.println("Creating trainer");
        // Check if trainer already exists
        if (customerRepository.existsByEmail(signupRequest.getEmail())) {
            return null;
        }

        Customer trainer = new Customer();
        BeanUtils.copyProperties(signupRequest, trainer);

        // Set role to ROLE_FORMATEUR for trainers
        trainer.setRoles(Collections.singleton(Role.ROLE_FORMATEUR));

        // Hash the password before saving
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        trainer.setPassword(hashPassword);

        // Save the trainer to the repository
        Customer createdTrainer = customerRepository.save(trainer);

        // Set the ID and return the created trainer
        createdTrainer.setId(createdTrainer.getId());
        return createdTrainer;
    }

	@Override
	 public List<Customer> getCustomersByRole(Role role) {
        return customerRepository.findByRolesContaining(role);
    }
	
	@Override
    public Optional<Customer> getFormateurById(Long id) {
        return customerRepository.findByIdAndRoles(id, Role.ROLE_FORMATEUR);
    }
}
