package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.entity.NutritionReportEntity;
import ru.razum0ff.WorkoutApp.entity.WeekNutritionEntity;

import java.util.List;
import java.util.UUID;

public interface NutritionReportService {
    List<NutritionReportEntity> getNutritionReportByUser(UUID userId);

    void addNutritionReport(UUID id, NutritionReportEntity nutritionReport);

    WeekNutritionEntity getLastWeekNutritionReportByUser(UUID userId);
}
