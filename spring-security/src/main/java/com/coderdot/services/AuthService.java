package com.coderdot.services;

import java.util.List;
import java.util.Optional;

import com.coderdot.dto.SignupRequest;
import com.coderdot.entities.Customer;

public interface AuthService {

    Customer createCustomer(SignupRequest signupRequest);

    Customer createTrainer(SignupRequest signupRequest);
    List<Customer> getCustomersByRole(Customer.Role role);
    Optional<Customer> getFormateurById(Long id);
}
