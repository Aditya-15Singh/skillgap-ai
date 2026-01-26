package com.skillgap.dto;

import com.skillgap.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillDTO {
    private String skillName;
    private UserProfile.ExperienceLevel skillLevel;
}
