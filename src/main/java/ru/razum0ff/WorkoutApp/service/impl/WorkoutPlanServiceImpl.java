package ru.razum0ff.WorkoutApp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.razum0ff.WorkoutApp.dto.body.WorkoutPlanFormBody;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.WorkoutPlan;
import ru.razum0ff.WorkoutApp.repository.UserRepository;
import ru.razum0ff.WorkoutApp.repository.WorkoutPlanRepository;
import ru.razum0ff.WorkoutApp.service.WorkoutPlanService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutPlanServiceImpl implements WorkoutPlanService {
    WorkoutPlanRepository repository;
    UserRepository userRepository;

    @Autowired
    public WorkoutPlanServiceImpl(WorkoutPlanRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<WorkoutPlanFormBody> getWorkoutPlan(UUID uuid) {
        UserEntity user = userRepository.findById(uuid).get();
        List<WorkoutPlan> workoutPlans = repository.findAllByUser(user);
        List<WorkoutPlanFormBody> workoutPlanFormBodyList = new ArrayList<>();
        for (WorkoutPlan plan : workoutPlans){
            WorkoutPlanFormBody body = new WorkoutPlanFormBody();
            body.setExercises(plan.getExercises().split(", "));
            body.setRepetitions(plan.getRepetitions().split(", "));
            body.setName(plan.getName());
            body.setId(plan.getId());
            workoutPlanFormBodyList.add(body);
        }
        return workoutPlanFormBodyList;
    }

    @Override
    public void createWorkoutPlan(WorkoutPlanFormBody body) {
        UserEntity user = userRepository.findById(body.getUserId()).get();
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setName(body.getName());
        workoutPlan.setExercises(Arrays.toString(body.getExercises()).replace("[", "").replace("]", ""));
        workoutPlan.setRepetitions(Arrays.toString(body.getRepetitions()).replace("[", "").replace("]", ""));
        workoutPlan.setUser(user);
        repository.save(workoutPlan);
    }

    @Override
    @Transactional
    public void deleteWorkoutPlanById(UUID planId) {
        repository.deleteById(planId);
    }
}
