package com.skillgap.service;

import com.skillgap.dto.UserProfileDTO;
import com.skillgap.entity.User;
import com.skillgap.entity.UserProfile;
import com.skillgap.repository.UserProfileRepository;
import com.skillgap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserProfileService {

        @Autowired
        private UserProfileRepository profileRepository;

        @Autowired
        private UserRepository userRepository;

        public UserProfile createOrUpdateProfile(Long userId, UserProfileDTO dto) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                UserProfile profile = profileRepository.findByUserId(userId)
                                .orElse(new UserProfile());

                profile.setUser(user);
                profile.setCareerGoal(dto.getCareerGoal());
                profile.setStudyHoursPerWeek(dto.getStudyHoursPerWeek());
                profile.setExperienceLevel(dto.getExperienceLevel());

                if (dto.getCurrentSkills() != null) {
                        profile.setCurrentSkills(
                                        dto.getCurrentSkills().stream()
                                                        .map(s -> new UserProfile.UserSkill(s.getSkillName(),
                                                                        s.getSkillLevel()))
                                                        .collect(Collectors.toList()));
                }

                return profileRepository.save(profile);
        }

        public UserProfile getProfile(Long userId) {
                return profileRepository.findByUserId(userId)
                                .orElseThrow(() -> new RuntimeException("Profile not found"));
        }
}
