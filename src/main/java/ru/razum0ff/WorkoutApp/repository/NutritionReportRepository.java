package ru.razum0ff.WorkoutApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.razum0ff.WorkoutApp.entity.NutritionReportEntity;
import ru.razum0ff.WorkoutApp.entity.UserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface NutritionReportRepository extends JpaRepository<NutritionReportEntity, UUID> {
    List<NutritionReportEntity> findAllByUserOrderByDateDesc(UserEntity user);
}
