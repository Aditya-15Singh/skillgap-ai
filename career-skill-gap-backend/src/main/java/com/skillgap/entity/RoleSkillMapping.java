package com.skillgap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role_skill_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSkillMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "career_role_id", nullable = false)
    private CareerRole careerRole;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_level")
    private RequiredLevel requiredLevel;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @ElementCollection
    @CollectionTable(name = "skill_prerequisites", joinColumns = @JoinColumn(name = "mapping_id"))
    @Column(name = "prerequisite_skill_id")
    private List<Long> prerequisiteSkillIds = new ArrayList<>();

    public enum RequiredLevel {
        BEGINNER, INTERMEDIATE, ADVANCED
    }

    public enum Importance {
        CRITICAL, HIGH, MEDIUM, LOW
    }
}
