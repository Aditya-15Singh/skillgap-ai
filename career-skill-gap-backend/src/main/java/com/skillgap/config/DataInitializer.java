package com.skillgap.config;

import com.skillgap.entity.*;
import com.skillgap.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private SkillRepository skillRepository;

        @Autowired
        private CareerRoleRepository roleRepository;

        @Autowired
        private RoleSkillMappingRepository mappingRepository;

        @Autowired
        private LearningResourceRepository resourceRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) {
                System.out.println("Initializing database with seed data...");

                // Create Admin User if not exists
                if (userRepository.findByEmail("admin@skillgap.com").isEmpty()) {
                        User admin = new User();
                        admin.setName("Admin User");
                        admin.setEmail("admin@skillgap.com");
                        admin.setPassword(passwordEncoder.encode("admin123"));
                        admin.setRole(User.Role.ADMIN);
                        userRepository.save(admin);
                }

                // Create Skills
                Skill java = createSkill("Java", "Backend", Skill.Difficulty.MEDIUM,
                                "Object-oriented programming language");
                Skill python = createSkill("Python", "Backend", Skill.Difficulty.EASY,
                                "Versatile programming language");
                Skill javascript = createSkill("JavaScript", "Frontend", Skill.Difficulty.MEDIUM,
                                "Web programming language");
                Skill react = createSkill("React", "Frontend", Skill.Difficulty.MEDIUM,
                                "JavaScript library for building UIs");
                Skill springBoot = createSkill("Spring Boot", "Backend", Skill.Difficulty.HARD,
                                "Java framework for web applications");
                Skill restAPI = createSkill("REST APIs", "Backend", Skill.Difficulty.MEDIUM, "RESTful web services");
                Skill mysql = createSkill("MySQL", "Database", Skill.Difficulty.MEDIUM,
                                "Relational database management");
                Skill mongodb = createSkill("MongoDB", "Database", Skill.Difficulty.MEDIUM, "NoSQL document database");
                Skill docker = createSkill("Docker", "DevOps", Skill.Difficulty.HARD, "Containerization platform");
                Skill git = createSkill("Git", "DevOps", Skill.Difficulty.EASY, "Version control system");
                Skill aws = createSkill("AWS", "DevOps", Skill.Difficulty.HARD, "Cloud computing platform");
                Skill html = createSkill("HTML", "Frontend", Skill.Difficulty.EASY, "Markup language for web pages");
                Skill css = createSkill("CSS", "Frontend", Skill.Difficulty.EASY, "Styling language for web pages");
                Skill nodejs = createSkill("Node.js", "Backend", Skill.Difficulty.MEDIUM,
                                "JavaScript runtime environment");
                Skill express = createSkill("Express.js", "Backend", Skill.Difficulty.MEDIUM,
                                "Web framework for Node.js");

                // Create Career Roles (20+ Roles)
                CareerRole backendDev = createRole("Backend Developer", "Develops server-side logic and databases",
                                "$70,000 - $120,000", "Software Development");
                CareerRole fullStackDev = createRole("Full Stack Developer", "Develops both frontend and backend",
                                "$80,000 - $140,000", "Software Development");
                CareerRole frontendDev = createRole("Frontend Developer", "Creates user interfaces and experiences",
                                "$60,000 - $110,000", "Software Development");

                createRole("DevOps Engineer", "Manages infrastructure and deployment pipelines", "$90,000 - $150,000",
                                "Operations");
                createRole("Data Scientist", "Analyzes complex data to help make decisions", "$95,000 - $160,000",
                                "Data Science");
                createRole("Machine Learning Engineer", "Builds AI models and systems", "$100,000 - $180,000", "AI/ML");
                createRole("Mobile App Developer (iOS)", "Builds apps for Apple devices", "$75,000 - $130,000",
                                "Mobile Development");
                createRole("Mobile App Developer (Android)", "Builds apps for Android devices", "$75,000 - $130,000",
                                "Mobile Development");
                createRole("UI/UX Designer", "Designs user interfaces and experiences", "$65,000 - $120,000", "Design");
                createRole("Cloud Architect", "Designs cloud infrastructure", "$120,000 - $180,000", "Cloud Computing");
                createRole("Cybersecurity Analyst", "Protects systems from cyber threats", "$80,000 - $140,000",
                                "Security");
                createRole("Game Developer", "Develops video games", "$70,000 - $130,000", "Game Development");
                createRole("Blockchain Developer", "Builds decentralized applications", "$100,000 - $170,000",
                                "Blockchain");
                createRole("QA Engineer", "Tests software for bugs", "$60,000 - $100,000", "Quality Assurance");
                createRole("Product Manager", "Manages product lifecycle", "$90,000 - $150,000", "Product Management");
                createRole("Database Administrator", "Manages databases", "$80,000 - $130,000",
                                "Database Administration");
                createRole("Network Engineer", "Manages computer networks", "$75,000 - $125,000", "Networking");
                createRole("Systems Administrator", "Manages computer systems", "$70,000 - $115,000", "IT Operations");
                createRole("AI Research Scientist", "Researches new AI algorithms", "$130,000 - $200,000",
                                "AI Research");
                createRole("Technical Lead", "Leads development teams", "$110,000 - $160,000", "Leadership");

                // Map Skills to Roles (Sample mappings for existing roles)
                if (roleRepository.findByName("Backend Developer").isPresent()) {
                        // Mappings already exist or handled by logic below if checking
                        // Note: Detailed mapping logic skipped for brevity on re-run to avoid
                        // duplication complexity
                        // Ideally we check before adding each mapping too.
                }

                // Add additional niche skills
                Skill kubernetes = createSkill("Kubernetes", "DevOps", Skill.Difficulty.HARD,
                                "Container orchestration");
                Skill terraform = createSkill("Terraform", "DevOps", Skill.Difficulty.MEDIUM, "Infrastructure as Code");
                Skill swift = createSkill("Swift", "Mobile", Skill.Difficulty.MEDIUM, "iOS programming language");
                Skill kotlin = createSkill("Kotlin", "Mobile", Skill.Difficulty.MEDIUM, "Android programming language");
                Skill figma = createSkill("Figma", "Design", Skill.Difficulty.EASY, "Interface design tool");

                // Map Skills to Backend Developer
                CareerRole backendRole = roleRepository.findByName("Backend Developer").get();
                addRoleSkillSafe(backendRole, java, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                RoleSkillMapping.Importance.CRITICAL);
                addRoleSkillSafe(backendRole, springBoot, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                RoleSkillMapping.Importance.CRITICAL);
                addRoleSkillSafe(backendRole, restAPI, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                RoleSkillMapping.Importance.CRITICAL);
                addRoleSkillSafe(backendRole, mysql, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                RoleSkillMapping.Importance.HIGH);
                addRoleSkillSafe(backendRole, git, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                RoleSkillMapping.Importance.HIGH);

                // Map Skills to DevOps Engineer
                roleRepository.findByName("DevOps Engineer").ifPresent(role -> {
                        addRoleSkillSafe(role, docker, RoleSkillMapping.RequiredLevel.ADVANCED,
                                        RoleSkillMapping.Importance.CRITICAL);
                        addRoleSkillSafe(role, aws, RoleSkillMapping.RequiredLevel.ADVANCED,
                                        RoleSkillMapping.Importance.CRITICAL);
                        addRoleSkillSafe(role, git, RoleSkillMapping.RequiredLevel.ADVANCED,
                                        RoleSkillMapping.Importance.HIGH);
                        addRoleSkillSafe(role, python, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                        RoleSkillMapping.Importance.MEDIUM);
                        addRoleSkillSafe(role, kubernetes, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                        RoleSkillMapping.Importance.CRITICAL);
                        addRoleSkillSafe(role, terraform, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                        RoleSkillMapping.Importance.HIGH);
                });

                // Map Skills to Data Scientist
                roleRepository.findByName("Data Scientist").ifPresent(role -> {
                        addRoleSkillSafe(role, python, RoleSkillMapping.RequiredLevel.ADVANCED,
                                        RoleSkillMapping.Importance.CRITICAL);
                        addRoleSkillSafe(role, mysql, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                        RoleSkillMapping.Importance.HIGH);
                        addRoleSkillSafe(role, aws, RoleSkillMapping.RequiredLevel.BEGINNER,
                                        RoleSkillMapping.Importance.MEDIUM);
                });

                // Map Skills to Mobile Dev (iOS)
                roleRepository.findByName("Mobile App Developer (iOS)").ifPresent(role -> {
                        addRoleSkillSafe(role, swift, RoleSkillMapping.RequiredLevel.ADVANCED,
                                        RoleSkillMapping.Importance.CRITICAL);
                        addRoleSkillSafe(role, git, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                        RoleSkillMapping.Importance.HIGH);
                        addRoleSkillSafe(role, restAPI, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                        RoleSkillMapping.Importance.HIGH);
                });

                // Map Skills to Mobile Dev (Android)
                roleRepository.findByName("Mobile App Developer (Android)").ifPresent(role -> {
                        addRoleSkillSafe(role, kotlin, RoleSkillMapping.RequiredLevel.ADVANCED,
                                        RoleSkillMapping.Importance.CRITICAL);
                        addRoleSkillSafe(role, java, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                        RoleSkillMapping.Importance.HIGH);
                        addRoleSkillSafe(role, git, RoleSkillMapping.RequiredLevel.INTERMEDIATE,
                                        RoleSkillMapping.Importance.HIGH);
                });

                // Map Skills to UI/UX
                roleRepository.findByName("UI/UX Designer").ifPresent(role -> {
                        addRoleSkillSafe(role, figma, RoleSkillMapping.RequiredLevel.ADVANCED,
                                        RoleSkillMapping.Importance.CRITICAL);
                        addRoleSkillSafe(role, html, RoleSkillMapping.RequiredLevel.BEGINNER,
                                        RoleSkillMapping.Importance.MEDIUM);
                        addRoleSkillSafe(role, css, RoleSkillMapping.RequiredLevel.BEGINNER,
                                        RoleSkillMapping.Importance.MEDIUM);
                });

                // Default mappings for others to avoid 0%
                roleRepository.findAll().forEach(role -> {
                        if (mappingRepository.findByCareerRole(role).isEmpty()) {
                                // Fallback: every unmapped role gets Git and Python/Java as basics
                                addRoleSkillSafe(role, git, RoleSkillMapping.RequiredLevel.BEGINNER,
                                                RoleSkillMapping.Importance.MEDIUM);
                        }
                });

                // Add Resource Helper (Safe add)
                addResourcesSafe(java, "Java Programming Masterclass", "Udemy",
                                "https://www.udemy.com/course/java-the-complete-java-developer-course/", false,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.COURSE);
                addResourcesSafe(python, "Complete Python Bootcamp", "Udemy",
                                "https://www.udemy.com/course/complete-python-bootcamp/", false,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.COURSE);
                addResourcesSafe(javascript, "The Complete JavaScript Course", "Udemy",
                                "https://www.udemy.com/course/the-complete-javascript-course/", false,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.COURSE);
                addResourcesSafe(javascript, "JavaScript.info", "Website", "https://javascript.info/", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.ARTICLE);
                addResourcesSafe(react, "React - The Complete Guide", "Udemy",
                                "https://www.udemy.com/course/react-the-complete-guide-incl-redux/", false,
                                LearningResource.DifficultyLevel.INTERMEDIATE, LearningResource.ResourceType.COURSE);
                addResourcesSafe(react, "React JS Full Course", "YouTube - freeCodeCamp",
                                "https://www.youtube.com/watch?v=bMknfKXIFA8", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.YOUTUBE);
                addResourcesSafe(html, "HTML Full Course", "YouTube - freeCodeCamp",
                                "https://www.youtube.com/watch?v=pQN-pnXPaVg", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.YOUTUBE);
                addResourcesSafe(html, "MDN Web Docs - HTML", "MDN",
                                "https://developer.mozilla.org/en-US/docs/Web/HTML", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.ARTICLE);
                addResourcesSafe(css, "CSS Full Course", "YouTube - freeCodeCamp",
                                "https://www.youtube.com/watch?v=1Rs2ND1ryYc", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.YOUTUBE);
                addResourcesSafe(css, "MDN Web Docs - CSS", "MDN", "https://developer.mozilla.org/en-US/docs/Web/CSS",
                                true, LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.ARTICLE);
                addResourcesSafe(nodejs, "Node.js Crash Course", "YouTube - Traversy Media",
                                "https://www.youtube.com/watch?v=fBNz5xF-Kx4", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.YOUTUBE);
                addResourcesSafe(git, "Git & GitHub Crash Course", "YouTube - freeCodeCamp",
                                "https://www.youtube.com/watch?v=RGOj5yH7evk", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.YOUTUBE);
                addResourcesSafe(docker, "Docker Tutorial for Beginners", "YouTube - freeCodeCamp",
                                "https://www.youtube.com/watch?v=fqMOX6JJhGo", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.YOUTUBE);
                addResourcesSafe(aws, "AWS Certified Cloud Practitioner", "YouTube - freeCodeCamp",
                                "https://www.youtube.com/watch?v=SOTamWNgDKc", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.YOUTUBE);
                addResourcesSafe(mysql, "MySQL Tutorial for Beginners", "YouTube - Programming with Mosh",
                                "https://www.youtube.com/watch?v=7S_tz1z_5bA", true,
                                LearningResource.DifficultyLevel.BEGINNER, LearningResource.ResourceType.YOUTUBE);

                System.out.println("Database initialization completed!");
        }

        private Skill createSkill(String name, String category, Skill.Difficulty difficulty, String description) {
                return skillRepository.findByName(name).orElseGet(() -> {
                        Skill skill = new Skill();
                        skill.setName(name);
                        skill.setCategory(category);
                        skill.setDifficulty(difficulty);
                        skill.setDescription(description);
                        return skillRepository.save(skill);
                });
        }

        private CareerRole createRole(String name, String description, String salaryRange, String industry) {
                return roleRepository.findByName(name).orElseGet(() -> {
                        CareerRole role = new CareerRole();
                        role.setName(name);
                        role.setDescription(description);
                        role.setAvgSalaryRange(salaryRange);
                        role.setIndustry(industry);
                        return roleRepository.save(role);
                });
        }

        private void addResourcesSafe(Skill skill, String title, String platform, String url, boolean isFree,
                        LearningResource.DifficultyLevel level, LearningResource.ResourceType type) {
                // Simple check to avoid duplicates (not perfect but good for seed)
                if (resourceRepository.findAll().stream().noneMatch(
                                r -> r.getTitle().equals(title) && r.getSkill().getId().equals(skill.getId()))) {
                        LearningResource resource = new LearningResource();
                        resource.setSkill(skill);
                        resource.setTitle(title);
                        resource.setType(type);
                        resource.setUrl(url);
                        resource.setPlatform(platform);
                        resource.setIsFree(isFree);
                        resource.setDifficultyLevel(level);
                        resourceRepository.save(resource);
                }
        }

        private void addRoleSkillSafe(CareerRole role, Skill skill, RoleSkillMapping.RequiredLevel level,
                        RoleSkillMapping.Importance importance) {
                // Check if mapping already exists to prevent duplicates
                List<RoleSkillMapping> existing = mappingRepository.findByCareerRole(role);
                boolean exists = existing.stream().anyMatch(m -> m.getSkill().getId().equals(skill.getId()));

                if (!exists) {
                        RoleSkillMapping mapping = new RoleSkillMapping();
                        mapping.setCareerRole(role);
                        mapping.setSkill(skill);
                        mapping.setRequiredLevel(level);
                        mapping.setImportance(importance);
                        mapping.setPrerequisiteSkillIds(java.util.Collections.emptyList());
                        mappingRepository.save(mapping);
                }
        }

        private void addRoleSkill(CareerRole role, Skill skill, RoleSkillMapping.RequiredLevel level,
                        RoleSkillMapping.Importance importance, List<Long> prerequisites) {
                // Simplified mapping adder (skipping duplicate check for now as mappings are
                // complex to equality check unique constraints usually handle strictness)
                // ideally implementation_plan should cover this. For now, skipping assumes
                // mappings exist if role exists from previous run.
        }
}
