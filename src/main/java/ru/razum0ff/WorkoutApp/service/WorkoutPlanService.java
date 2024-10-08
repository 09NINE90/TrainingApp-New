package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;

import java.util.UUID;

public interface WorkoutPlanService {
    String getWorkoutPlan(UUID uuid);
}
