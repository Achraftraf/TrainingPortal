package com.coderdot.services;

import com.coderdot.dto.SignupRequest;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Customer.Role;
import com.coderdot.repository.CustomerRepository;

import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
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
}
