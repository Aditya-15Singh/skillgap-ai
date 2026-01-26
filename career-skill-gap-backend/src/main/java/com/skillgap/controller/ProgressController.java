package com.skillgap.controller;

import com.skillgap.entity.LearningResource;
import com.skillgap.entity.User;
import com.skillgap.entity.UserProgress;
import com.skillgap.repository.LearningResourceRepository;
import com.skillgap.repository.UserProgressRepository;
import com.skillgap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*")
public class ProgressController {

    @Autowired
    private UserProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LearningResourceRepository resourceRepository;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProgressController.class);

    @PostMapping("/{resourceId}")
    @Transactional
    public ResponseEntity<?> toggleProgress(@PathVariable Long resourceId) {
        User user = getCurrentUser();
        logger.info("Toggling progress for user {} and resource {}", user.getId(), resourceId);

        LearningResource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        Optional<UserProgress> existing = progressRepository.findByUserIdAndResourceId(user.getId(), resourceId);

        if (existing.isPresent()) {
            progressRepository.deleteByUserIdAndResourceId(user.getId(), resourceId);
            logger.info("Removed progress for user {} and resource {}", user.getId(), resourceId);
            return ResponseEntity.ok(Map.of("status", "removed"));
        } else {
            UserProgress progress = new UserProgress(user, resource);
            progressRepository.save(progress);
            logger.info("Added progress for user {} and resource {}", user.getId(), resourceId);
            return ResponseEntity.ok(Map.of("status", "added"));
        }
    }

    @GetMapping
    public ResponseEntity<List<Long>> getCompletedResourceIds() {
        User user = getCurrentUser();
        List<UserProgress> progressList = progressRepository.findByUserId(user.getId());
        List<Long> completedIds = progressList.stream()
                .map(p -> p.getResource().getId())
                .collect(Collectors.toList());
        return ResponseEntity.ok(completedIds);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getProgressSummary() {
        User user = getCurrentUser();
        long completedCount = progressRepository.countByUserId(user.getId());

        Map<String, Object> summary = new HashMap<>();
        summary.put("completedCount", completedCount);
        // Additional stats can be added here (e.g. total resources based on current
        // goal)

        return ResponseEntity.ok(summary);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
