package com.coderdot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/signup", "/login","/error","/formateurs/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/**")
                .authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/admin/**")
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
// =======
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.coderdot.entities.Formateur;
// import com.coderdot.services.FormateurService;

// @RestController
// //@RequestMapping("/api/formateurs")
// @CrossOrigin(origins = "*")
// public class FormateurController {
// >>>>>>> lamiae_2

	@Autowired
	private FormateurService formateurService;
	
	@PostMapping("/inscrire")
	public ResponseEntity<Formateur> inscrireFormateur(@RequestBody Formateur formateur) {
     Formateur inscritFormateur = formateurService.inscrireFormateur(formateur);
     return new ResponseEntity<>(inscritFormateur, HttpStatus.CREATED);
	}
	 @GetMapping("/api/formateurs/all")
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<List<Formateur>> getAllFormateurs() {
	        List<Formateur> allFormateurs = formateurService.getAllFormateurs();
	        return new ResponseEntity<>(allFormateurs, HttpStatus.OK);
	    }
	

	 	@PutMapping("/api/formateurs/accepter/{formateurId}")
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

	 	@DeleteMapping("/api/formateurs/refuser/{formateurId}")
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

