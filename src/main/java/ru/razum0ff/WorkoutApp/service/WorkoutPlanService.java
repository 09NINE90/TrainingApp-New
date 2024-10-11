package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.dto.body.WorkoutPlanFormBody;

import java.util.List;
import java.util.UUID;

public interface WorkoutPlanService {
    List<WorkoutPlanFormBody> getWorkoutPlanByUser(UUID uuid);
    void createWorkoutPlan(WorkoutPlanFormBody body);

    void deleteWorkoutPlanById(UUID planId);

    WorkoutPlanFormBody getWorkoutPlanById(UUID workoutPlanId);
}
