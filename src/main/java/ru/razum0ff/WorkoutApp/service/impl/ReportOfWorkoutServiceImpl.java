package ru.razum0ff.WorkoutApp.service.impl;

import org.springframework.stereotype.Service;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.dto.body.ReportOfWorkoutBody;
import ru.razum0ff.WorkoutApp.entity.ReportOfWorkoutEntity;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.repository.ReportOfWorkoutRepository;
import ru.razum0ff.WorkoutApp.repository.UserRepository;
import ru.razum0ff.WorkoutApp.service.ReportOfWorkoutService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ReportOfWorkoutServiceImpl implements ReportOfWorkoutService {
    final
    ReportOfWorkoutRepository repository;
    UserRepository userRepository;

    public ReportOfWorkoutServiceImpl(ReportOfWorkoutRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveReportOfWorkout(CustomUserDetails userDetails, ReportOfWorkoutBody report) {
        UUID userId = userDetails.getId();
        UserEntity user = userRepository.findById(userId).get();
        ReportOfWorkoutEntity reportOfWorkout = new ReportOfWorkoutEntity();
        reportOfWorkout.setDate(report.getDate());
        reportOfWorkout.setUser(user);
        reportOfWorkout.setExercises(Arrays.toString(report.getExercises()).replace("[", "").replace("]", ""));
        reportOfWorkout.setReports(Arrays.toString(report.getReports()).replace("[", "").replace("]", ""));
        repository.save(reportOfWorkout);
    }

    @Override
    public List<ReportOfWorkoutBody> getReportOfWorkoutByUser(UUID userId) {
        UserEntity user = userRepository.findById(userId).get();
        List<ReportOfWorkoutEntity> reportOfWorkoutEntities = repository.findAllByUserOrderByDateDesc(user);
        List<ReportOfWorkoutBody> bodyList = new ArrayList<>();
        for (ReportOfWorkoutEntity report : reportOfWorkoutEntities){
            ReportOfWorkoutBody body = new ReportOfWorkoutBody();
            body.setId(report.getId());
            body.setDate(report.getDate());
            body.setExercises(report.getExercises().split(", "));
            body.setReports(report.getReports().split(", "));
            bodyList.add(body);
        }
        return bodyList;
    }
    @Override
    public ReportOfWorkoutBody getReportOfWorkoutById(UUID reportOfWorkoutId) {
        ReportOfWorkoutEntity reportOfWorkout = repository.findById(reportOfWorkoutId).get();
        ReportOfWorkoutBody body = new ReportOfWorkoutBody();
        body.setUserId(reportOfWorkout.getUser().getId());
        body.setDate(reportOfWorkout.getDate());
        body.setExercises(reportOfWorkout.getExercises().split(", "));
        body.setReports(reportOfWorkout.getReports().split(", "));
        return body;
    }
    @Override
    public void updateReportOfWorkoutById(UUID reportOfWorkoutId, ReportOfWorkoutBody body) {
        UserEntity user = userRepository.findById(body.getUserId()).get();
        ReportOfWorkoutEntity reportOfWorkout = new ReportOfWorkoutEntity();
        reportOfWorkout.setId(reportOfWorkoutId);
        reportOfWorkout.setUser(user);
        reportOfWorkout.setExercises(Arrays.toString(body.getExercises()).replace("[", "").replace("]", ""));
        reportOfWorkout.setReports(Arrays.toString(body.getReports()).replace("[", "").replace("]", ""));
        reportOfWorkout.setDate(body.getDate());
        repository.save(reportOfWorkout);
    }
}
