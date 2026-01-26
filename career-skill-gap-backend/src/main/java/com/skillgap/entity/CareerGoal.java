package com.skillgap.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "career_goal", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id" }))
public class CareerGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "goal_name", nullable = false)
    private String goalName;

    public CareerGoal() {
    }

    public CareerGoal(User user, String goalName) {
        this.user = user;
        this.goalName = goalName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }
}
