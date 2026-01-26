package com.skillgap.controller;

import com.skillgap.dto.LearningRoadmapDTO;
import com.skillgap.dto.SkillGapResponse;
import com.skillgap.entity.User;
import com.skillgap.repository.UserRepository;
import com.skillgap.service.AISkillGapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*")
public class AnalysisController {

    @Autowired
    private AISkillGapService aiService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/skill-gap")
    public ResponseEntity<SkillGapResponse> analyzeSkillGap(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SkillGapResponse analysis = aiService.analyzeSkillGap(user.getId());
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/roadmap")
    public ResponseEntity<LearningRoadmapDTO> getLearningRoadmap(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LearningRoadmapDTO roadmap = aiService.generateLearningRoadmap(user.getId());
        return ResponseEntity.ok(roadmap);
    }
}
