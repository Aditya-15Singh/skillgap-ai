package com.skillgap.dto;

import com.skillgap.entity.UserProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    @NotBlank(message = "Career goal is required")
    private String careerGoal;

    @NotNull(message = "Study hours per week is required")
    private Integer studyHoursPerWeek;

    private UserProfile.ExperienceLevel experienceLevel;

    private List<UserSkillDTO> currentSkills;

    private Boolean emailRemindersEnabled;
}
