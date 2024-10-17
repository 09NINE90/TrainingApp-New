package ru.razum0ff.WorkoutApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.WeekNutritionEntity;

import java.util.Date;
import java.util.UUID;

@Repository
public interface WeekNutritionRepository extends JpaRepository<WeekNutritionEntity, UUID> {

    WeekNutritionEntity findByUserAndWeekStart(UserEntity user, Date weekStart);

    WeekNutritionEntity findFirstByUserOrderByWeekStartDesc(UserEntity user);

}
