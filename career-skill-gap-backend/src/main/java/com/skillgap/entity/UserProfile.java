package com.skillgap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "career_goal", nullable = false)
    private String careerGoal;

    @Column(name = "study_hours_per_week")
    private Integer studyHoursPerWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level")
    private ExperienceLevel experienceLevel;

    @ElementCollection
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "profile_id"))
    private List<UserSkill> currentSkills = new ArrayList<>();

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "email_reminders_enabled")
    private Boolean emailRemindersEnabled = true;

    public enum ExperienceLevel {
        BEGINNER, INTERMEDIATE, ADVANCED
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSkill {
        @Column(name = "skill_name")
        private String skillName;

        @Enumerated(EnumType.STRING)
        @Column(name = "skill_level")
        private ExperienceLevel skillLevel;
    }
}
