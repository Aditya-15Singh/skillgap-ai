package com.skillgap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "learning_resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Column(length = 500)
    private String url;

    @Column(length = 100)
    private String platform; // Udemy, Coursera, YouTube, etc.

    @Column(name = "is_free")
    private Boolean isFree = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private DifficultyLevel difficultyLevel;

    public enum ResourceType {
        COURSE, YOUTUBE, ARTICLE, BOOK, PRACTICE
    }

    public enum DifficultyLevel {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
}
