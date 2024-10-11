package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.dto.body.ReportOfWorkoutBody;

import java.util.List;
import java.util.UUID;

public interface ReportOfWorkoutService {
    void saveReportOfWorkout(CustomUserDetails userDetails, ReportOfWorkoutBody report);

    List<ReportOfWorkoutBody> getReportOfWorkoutByUser(UUID userId);

    ReportOfWorkoutBody getReportOfWorkoutById(UUID reportOfWorkoutId);

    void updateReportOfWorkoutById(UUID reportOfWorkoutId, ReportOfWorkoutBody body);
}
