package com.skillgap.controller;

import com.skillgap.dto.UserProfileDTO;
import com.skillgap.entity.User;
import com.skillgap.entity.UserProfile;
import com.skillgap.repository.UserRepository;
import com.skillgap.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")

public class UserProfileController {

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserProfile> createOrUpdateProfile(
            @Valid @RequestBody UserProfileDTO dto,
            Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = profileService.createOrUpdateProfile(user.getId(), dto);
        return ResponseEntity.ok(profile);
    }

    @GetMapping
    public ResponseEntity<UserProfile> getProfile(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = profileService.getProfile(user.getId());
        return ResponseEntity.ok(profile);
    }
}
