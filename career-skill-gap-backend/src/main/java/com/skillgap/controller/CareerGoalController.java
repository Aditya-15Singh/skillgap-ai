package com.skillgap.controller;

import com.skillgap.dto.CareerGoalDto;
import com.skillgap.entity.CareerGoal;
import com.skillgap.entity.User;
import com.skillgap.repository.CareerGoalRepository;
import com.skillgap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/career-goal")

public class CareerGoalController {

    @Autowired
    private CareerGoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getGoal() {
        User user = getCurrentUser();
        Optional<CareerGoal> goal = goalRepository.findByUser(user);
        if (goal.isPresent()) {
            return ResponseEntity.ok(goal.get());
        } else {
            return ResponseEntity.ok(null); // Or 204 No Content
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createGoal(@RequestBody CareerGoalDto dto) {
        User user = getCurrentUser();
        if (goalRepository.findByUser(user).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already has a career goal. Delete the existing one first.");
        }
        CareerGoal goal = new CareerGoal(user, dto.getGoalName());
        goalRepository.save(goal);
        return ResponseEntity.ok(goal);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deleteGoal() {
        User user = getCurrentUser();
        Optional<CareerGoal> goal = goalRepository.findByUser(user);

        if (goal.isPresent()) {
            goalRepository.delete(goal.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
