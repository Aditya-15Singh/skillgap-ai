package com.skillgap.repository;

import com.skillgap.entity.CareerGoal;
import com.skillgap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerGoalRepository extends JpaRepository<CareerGoal, Long> {
    Optional<CareerGoal> findByUser(User user);

    Optional<CareerGoal> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
