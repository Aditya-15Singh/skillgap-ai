package com.skillgap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "skill_gap_analyses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillGapAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "career_role_id", nullable = false)
    private CareerRole careerRole;

    @Column(columnDefinition = "JSON")
    private String analysisData; // JSON: gap scores, missing skills

    @Column(columnDefinition = "JSON")
    private String roadmapData; // JSON: ordered learning plan

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
