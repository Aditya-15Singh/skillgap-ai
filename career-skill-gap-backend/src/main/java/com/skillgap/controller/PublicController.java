package com.skillgap.controller;

import com.skillgap.entity.CareerRole;
import com.skillgap.entity.Skill;
import com.skillgap.service.CareerRoleService;
import com.skillgap.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private CareerRoleService roleService;

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<CareerRole>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
