package com.coderdot.services.jwt;

import com.coderdot.entities.Customer;
import com.coderdot.entities.Customer.Role;
import com.coderdot.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));

        Collection<? extends GrantedAuthority> authorities = getAuthorities(customer.getRoles());
        return new User(customer.getEmail(), customer.getPassword(), authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Customer.Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    public Set<String> getRoles(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));

        return customer.getRoles().stream()
                .map(Customer.Role::name)
                .collect(Collectors.toSet());
    }
    
    
    
   
}
