package com.skillgap.service;

import com.skillgap.dto.LearningRoadmapDTO;
import com.skillgap.entity.User;
import com.skillgap.entity.UserProfile;
import com.skillgap.entity.UserProgress;
import com.skillgap.repository.UserProgressRepository;
import com.skillgap.repository.UserRepository;
import com.skillgap.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmailReminderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private UserProgressRepository progressRepository;

    @Autowired
    private AISkillGapService aiService;

    // Runs every day at 8 AM
    @Scheduled(cron = "0 0 8 * * *")
    @Transactional(readOnly = true)
    public void sendDailyReminders() {
        List<UserProfile> profiles = profileRepository.findAll();
        for (UserProfile profile : profiles) {
            // Check if reminders are enabled
            if (Boolean.FALSE.equals(profile.getEmailRemindersEnabled())) {
                continue;
            }

            User user = profile.getUser();
            try {
                // Get Completed Resources
                List<UserProgress> progressList = progressRepository.findByUserId(user.getId());
                Set<Long> completedIds = progressList.stream()
                        .map(p -> p.getResource().getId())
                        .collect(Collectors.toSet());

                // Get Roadmap (Pending Resources)
                LearningRoadmapDTO roadmap = aiService.generateLearningRoadmap(user.getId());

                List<LearningRoadmapDTO.LearningResourceDTO> pendingResources = new ArrayList<>();
                if (roadmap != null && roadmap.getPhases() != null) {
                    for (LearningRoadmapDTO.RoadmapPhase phase : roadmap.getPhases()) {
                        if (phase.getSkills() == null)
                            continue;
                        for (LearningRoadmapDTO.SkillToLearn skill : phase.getSkills()) {
                            if (skill.getResources() == null)
                                continue;
                            for (LearningRoadmapDTO.LearningResourceDTO resource : skill.getResources()) {
                                if (resource.getId() != null && !completedIds.contains(resource.getId())) {
                                    pendingResources.add(resource);
                                }
                            }
                        }
                    }
                }

                if (!pendingResources.isEmpty()) {
                    sendEmail(user, pendingResources);
                }

            } catch (Exception e) {
                System.out.println("Skipping reminder for user " + user.getEmail() + ": " + e.getMessage());
            }
        }
    }

    private void sendEmail(User user, List<LearningRoadmapDTO.LearningResourceDTO> pendingResources) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@skillgap.com");
        message.setTo(user.getEmail());
        message.setSubject("Your Daily Study Link - Keep Going!");

        StringBuilder sb = new StringBuilder();
        sb.append("Hi ").append(user.getName()).append(",\n\n");
        sb.append("Here are some learning resources to focus on today to reach your career goal:\n\n");

        int count = 0;
        for (LearningRoadmapDTO.LearningResourceDTO r : pendingResources) {
            if (count >= 5)
                break;
            sb.append("- ").append(r.getTitle())
                    .append(" (").append(r.getPlatform()).append(")\n")
                    .append("  Link: ").append(r.getUrl()).append("\n\n");
            count++;
        }

        sb.append("You have ").append(pendingResources.size()).append(" total items pending.\n");
        sb.append("Log in to track your progress!\n\n");
        sb.append("Best,\nSkillGap Team");

        message.setText(sb.toString());
        mailSender.send(message);
        System.out.println("Sent reminder email to " + user.getEmail());
    }
}
