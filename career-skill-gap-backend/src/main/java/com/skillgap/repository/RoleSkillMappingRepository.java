package com.skillgap.repository;

import com.skillgap.entity.RoleSkillMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleSkillMappingRepository extends JpaRepository<RoleSkillMapping, Long> {
    List<RoleSkillMapping> findByCareerRoleId(Long careerRoleId);

    List<RoleSkillMapping> findByCareerRole(com.skillgap.entity.CareerRole careerRole);
}
