package com.skillgap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningRoadmapDTO {
    private String careerRole;
    private List<RoadmapPhase> phases;
    private Integer estimatedWeeks;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoadmapPhase {
        private Integer phaseNumber;
        private String phaseName;
        private List<SkillToLearn> skills;
        private Integer estimatedWeeks;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillToLearn {
        private String skillName;
        private String targetLevel;
        private List<LearningResourceDTO> resources;
        private Integer priority;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LearningResourceDTO {
        private Long id;
        private String title;
        private String type;
        private String url;
        private String platform;
        private Boolean isFree;
    }
}
