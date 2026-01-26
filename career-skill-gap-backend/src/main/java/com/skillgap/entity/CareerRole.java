package com.skillgap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "career_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "avg_salary_range", length = 50)
    private String avgSalaryRange;

    @Column(length = 100)
    private String industry;
}
