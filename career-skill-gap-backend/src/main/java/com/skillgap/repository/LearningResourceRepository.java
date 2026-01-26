package com.skillgap.repository;

import com.skillgap.entity.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningResourceRepository extends JpaRepository<LearningResource, Long> {
    List<LearningResource> findBySkillId(Long skillId);

    List<LearningResource> findBySkillIdAndDifficultyLevel(Long skillId, LearningResource.DifficultyLevel level);
}
