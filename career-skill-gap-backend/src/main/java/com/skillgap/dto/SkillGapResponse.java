package com.skillgap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillGapResponse {
    private String careerRole;
    private List<SkillGapItem> missingSkills;
    private List<SkillGapItem> skillsToImprove;
    private Double overallMatchPercentage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillGapItem {
        private String skillName;
        private String currentLevel;
        private String requiredLevel;
        private Integer gapScore;
        private String importance;
        private List<String> prerequisites;
    }
}
