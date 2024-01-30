package com.coderdot.dto;

import com.coderdot.entities.Customer.Role;

import java.util.Set;  // Import the correct Set interface

public class SignupRequest {

    private String email;
    private String name;
    private String password;
    private String skills; 

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    // Correct implementation of getRoles method
    public Set<Role> getRoles() {
        // You can return an empty set or default roles if needed
        return Set.of(Role.ROLE_USER);
    }
}
