package com.skillgap.service;

import com.skillgap.entity.CareerRole;
import com.skillgap.entity.RoleSkillMapping;
import com.skillgap.repository.CareerRoleRepository;
import com.skillgap.repository.RoleSkillMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerRoleService {

    @Autowired
    private CareerRoleRepository roleRepository;

    @Autowired
    private RoleSkillMappingRepository mappingRepository;

    public List<CareerRole> getAllRoles() {
        return roleRepository.findAll();
    }

    public CareerRole getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career role not found"));
    }

    public CareerRole createRole(CareerRole role) {
        return roleRepository.save(role);
    }

    public CareerRole updateRole(Long id, CareerRole roleDetails) {
        CareerRole role = getRoleById(id);
        role.setName(roleDetails.getName());
        role.setDescription(roleDetails.getDescription());
        role.setAvgSalaryRange(roleDetails.getAvgSalaryRange());
        role.setIndustry(roleDetails.getIndustry());
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public List<RoleSkillMapping> getRoleSkills(Long roleId) {
        return mappingRepository.findByCareerRoleId(roleId);
    }

    public RoleSkillMapping addSkillToRole(RoleSkillMapping mapping) {
        return mappingRepository.save(mapping);
    }

    public void removeSkillFromRole(Long mappingId) {
        mappingRepository.deleteById(mappingId);
    }
}
