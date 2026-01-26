package com.skillgap.controller;

import com.skillgap.entity.CareerRole;
import com.skillgap.entity.RoleSkillMapping;
import com.skillgap.entity.Skill;
import com.skillgap.service.CareerRoleService;
import com.skillgap.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private CareerRoleService roleService;

    // Skill Management
    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        return ResponseEntity.ok(skillService.createSkill(skill));
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        return ResponseEntity.ok(skillService.updateSkill(id, skill));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok().build();
    }

    // Career Role Management
    @GetMapping("/roles")
    public ResponseEntity<List<CareerRole>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/roles")
    public ResponseEntity<CareerRole> createRole(@RequestBody CareerRole role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<CareerRole> updateRole(@PathVariable Long id, @RequestBody CareerRole role) {
        return ResponseEntity.ok(roleService.updateRole(id, role));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    // Role-Skill Mapping
    @GetMapping("/roles/{roleId}/skills")
    public ResponseEntity<List<RoleSkillMapping>> getRoleSkills(@PathVariable Long roleId) {
        return ResponseEntity.ok(roleService.getRoleSkills(roleId));
    }

    @PostMapping("/role-skills")
    public ResponseEntity<RoleSkillMapping> addSkillToRole(@RequestBody RoleSkillMapping mapping) {
        return ResponseEntity.ok(roleService.addSkillToRole(mapping));
    }

    @DeleteMapping("/role-skills/{mappingId}")
    public ResponseEntity<Void> removeSkillFromRole(@PathVariable Long mappingId) {
        roleService.removeSkillFromRole(mappingId);
        return ResponseEntity.ok().build();
    }
}
