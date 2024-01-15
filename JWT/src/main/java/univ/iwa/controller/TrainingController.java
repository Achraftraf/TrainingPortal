package univ.iwa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import univ.iwa.model.Training;
import univ.iwa.service.TrainingService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth/admin/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    
//    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Training> addTraining(@RequestBody Training training) {
        try {
            // Ensure that the authenticated user has the 'ROLE_ADMIN' authority
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Training addedTraining = trainingService.addTraining(training);
            return new ResponseEntity<>(addedTraining, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace(); // You can replace this with your logging mechanism
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> allTrainings = trainingService.getAllTrainings();
        return new ResponseEntity<>(allTrainings, HttpStatus.OK);
    }    
   
    
    @GetMapping("/message")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<String>("Hello world", HttpStatus.OK);
    }

    // Additional endpoints as needed
}
