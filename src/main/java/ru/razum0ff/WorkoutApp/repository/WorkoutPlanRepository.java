package ru.razum0ff.WorkoutApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.WorkoutPlanEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlanEntity, UUID> {
    List<WorkoutPlanEntity> findAllByUser(UserEntity user);
}
