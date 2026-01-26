package com.skillgap.service;

import com.skillgap.dto.LearningRoadmapDTO;
import com.skillgap.dto.SkillGapResponse;
import com.skillgap.entity.*;
import com.skillgap.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AISkillGapService {

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private CareerRoleRepository careerRoleRepository;

    @Autowired
    private RoleSkillMappingRepository mappingRepository;

    @Autowired
    private LearningResourceRepository resourceRepository;

    @Autowired
    private SkillRepository skillRepository;

    /**
     * AI SKILL GAP ANALYSIS ENGINE
     * Compares user's current skills with required skills for career goal
     */
    public SkillGapResponse analyzeSkillGap(Long userId) {
        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        CareerRole careerRole = careerRoleRepository.findByName(profile.getCareerGoal())
                .orElseThrow(() -> new RuntimeException("Career role not found"));

        List<RoleSkillMapping> requiredSkills = mappingRepository.findByCareerRoleId(careerRole.getId());

        List<SkillGapResponse.SkillGapItem> missingSkills = new ArrayList<>();
        List<SkillGapResponse.SkillGapItem> skillsToImprove = new ArrayList<>();

        int totalRequiredSkills = requiredSkills.size();
        int matchedSkills = 0;

        for (RoleSkillMapping mapping : requiredSkills) {
            String skillName = mapping.getSkill().getName();
            int requiredLevel = getLevelValue(mapping.getRequiredLevel());

            // Find user's current level for this skill
            Optional<UserProfile.UserSkill> userSkillOpt = profile.getCurrentSkills().stream()
                    .filter(s -> s.getSkillName().equalsIgnoreCase(skillName))
                    .findFirst();

            int currentLevel = userSkillOpt.map(s -> getLevelValue(s.getSkillLevel())).orElse(0);

            // Calculate gap score using AI formula
            int gapScore = calculateGapScore(requiredLevel, currentLevel, mapping.getImportance());

            // Get prerequisites
            List<String> prerequisites = getPrerequisiteNames(mapping.getPrerequisiteSkillIds());

            SkillGapResponse.SkillGapItem item = new SkillGapResponse.SkillGapItem(
                    skillName,
                    currentLevel == 0 ? "None" : getLevelName(currentLevel),
                    getLevelName(requiredLevel),
                    gapScore,
                    mapping.getImportance().name(),
                    prerequisites);

            if (currentLevel == 0) {
                missingSkills.add(item);
            } else if (currentLevel < requiredLevel) {
                skillsToImprove.add(item);
                matchedSkills++; // Partial match
            } else {
                matchedSkills++; // Full match
            }
        }

        // Sort by gap score (higher = more important to learn)
        missingSkills.sort((a, b) -> b.getGapScore().compareTo(a.getGapScore()));
        skillsToImprove.sort((a, b) -> b.getGapScore().compareTo(a.getGapScore()));

        double matchPercentage = totalRequiredSkills > 0
                ? (matchedSkills * 100.0) / totalRequiredSkills
                : 0.0;

        return new SkillGapResponse(
                careerRole.getName(),
                missingSkills,
                skillsToImprove,
                Math.round(matchPercentage * 100.0) / 100.0);
    }

    /**
     * AI LEARNING ROADMAP GENERATOR
     * Creates personalized learning path based on prerequisites and study time
     */
    public LearningRoadmapDTO generateLearningRoadmap(Long userId) {
        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        SkillGapResponse gapAnalysis = analyzeSkillGap(userId);

        // Combine missing skills and skills to improve
        List<SkillGapResponse.SkillGapItem> allSkillsToLearn = new ArrayList<>();
        allSkillsToLearn.addAll(gapAnalysis.getMissingSkills());
        allSkillsToLearn.addAll(gapAnalysis.getSkillsToImprove());

        // Phase 1: Critical skills with no prerequisites
        // Phase 2: High importance skills or skills with prerequisites met
        // Phase 3: Remaining skills

        List<LearningRoadmapDTO.RoadmapPhase> phases = new ArrayList<>();
        Set<String> learnedSkills = profile.getCurrentSkills().stream()
                .map(UserProfile.UserSkill::getSkillName)
                .collect(Collectors.toSet());

        // Phase 1: Foundation skills (CRITICAL, no prerequisites)
        List<SkillGapResponse.SkillGapItem> phase1Skills = allSkillsToLearn.stream()
                .filter(s -> s.getImportance().equals("CRITICAL"))
                .filter(s -> s.getPrerequisites().isEmpty() ||
                        s.getPrerequisites().stream().allMatch(learnedSkills::contains))
                .collect(Collectors.toList());

        if (!phase1Skills.isEmpty()) {
            phases.add(createPhase(1, "Foundation - Critical Skills", phase1Skills, learnedSkills));
            phase1Skills.forEach(s -> learnedSkills.add(s.getSkillName()));
        }

        // Phase 2: Core skills (HIGH importance)
        List<SkillGapResponse.SkillGapItem> phase2Skills = allSkillsToLearn.stream()
                .filter(s -> !phase1Skills.contains(s))
                .filter(s -> s.getImportance().equals("HIGH") || s.getImportance().equals("CRITICAL"))
                .filter(s -> s.getPrerequisites().isEmpty() ||
                        s.getPrerequisites().stream().allMatch(learnedSkills::contains))
                .collect(Collectors.toList());

        if (!phase2Skills.isEmpty()) {
            phases.add(createPhase(2, "Core Skills", phase2Skills, learnedSkills));
            phase2Skills.forEach(s -> learnedSkills.add(s.getSkillName()));
        }

        // Phase 3: Advanced and optional skills
        List<SkillGapResponse.SkillGapItem> phase3Skills = allSkillsToLearn.stream()
                .filter(s -> !phase1Skills.contains(s) && !phase2Skills.contains(s))
                .collect(Collectors.toList());

        if (!phase3Skills.isEmpty()) {
            phases.add(createPhase(3, "Advanced & Enhancement", phase3Skills, learnedSkills));
        }

        int totalWeeks = phases.stream()
                .mapToInt(LearningRoadmapDTO.RoadmapPhase::getEstimatedWeeks)
                .sum();

        return new LearningRoadmapDTO(gapAnalysis.getCareerRole(), phases, totalWeeks);
    }

    private LearningRoadmapDTO.RoadmapPhase createPhase(int phaseNum, String name,
            List<SkillGapResponse.SkillGapItem> items,
            Set<String> learnedSkills) {
        List<LearningRoadmapDTO.SkillToLearn> skills = items.stream()
                .map(item -> {
                    Skill skill = skillRepository.findByName(item.getSkillName()).orElse(null);
                    List<LearningResource> resources = skill != null
                            ? resourceRepository.findBySkillId(skill.getId())
                            : new ArrayList<>();

                    List<LearningRoadmapDTO.LearningResourceDTO> resourceDTOs = resources.stream()
                            .limit(3) // Top 3 resources
                            .map(r -> new LearningRoadmapDTO.LearningResourceDTO(
                                    r.getId(),
                                    r.getTitle(),
                                    r.getType().name(),
                                    r.getUrl(),
                                    r.getPlatform(),
                                    r.getIsFree()))
                            .collect(Collectors.toList());

                    return new LearningRoadmapDTO.SkillToLearn(
                            item.getSkillName(),
                            item.getRequiredLevel(),
                            resourceDTOs,
                            item.getGapScore());
                })
                .collect(Collectors.toList());

        // Estimate 2-3 weeks per skill on average
        int estimatedWeeks = skills.size() * 3;

        return new LearningRoadmapDTO.RoadmapPhase(phaseNum, name, skills, estimatedWeeks);
    }

    /**
     * AI Gap Score Formula:
     * Gap Score = (Required Level - Current Level) × Importance Weight
     */
    private int calculateGapScore(int requiredLevel, int currentLevel, RoleSkillMapping.Importance importance) {
        int levelGap = requiredLevel - currentLevel;
        int importanceWeight = getImportanceWeight(importance);
        return levelGap * importanceWeight;
    }

    private int getImportanceWeight(RoleSkillMapping.Importance importance) {
        return switch (importance) {
            case CRITICAL -> 4;
            case HIGH -> 3;
            case MEDIUM -> 2;
            case LOW -> 1;
        };
    }

    private int getLevelValue(Object level) {
        if (level == null)
            return 0;

        String levelStr = level.toString();
        return switch (levelStr) {
            case "BEGINNER" -> 1;
            case "INTERMEDIATE" -> 2;
            case "ADVANCED" -> 3;
            default -> 0;
        };
    }

    private String getLevelName(int value) {
        return switch (value) {
            case 1 -> "BEGINNER";
            case 2 -> "INTERMEDIATE";
            case 3 -> "ADVANCED";
            default -> "NONE";
        };
    }

    private List<String> getPrerequisiteNames(List<Long> ids) {
        if (ids == null || ids.isEmpty())
            return new ArrayList<>();

        return ids.stream()
                .map(id -> skillRepository.findById(id))
                .filter(Optional::isPresent)
                .map(opt -> opt.get().getName())
                .collect(Collectors.toList());
    }
}
