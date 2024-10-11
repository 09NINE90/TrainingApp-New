package ru.razum0ff.WorkoutApp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.razum0ff.WorkoutApp.dto.body.WorkoutPlanFormBody;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.WorkoutPlanEntity;
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
    public List<WorkoutPlanFormBody> getWorkoutPlanByUser(UUID uuid) {
        UserEntity user = userRepository.findById(uuid).get();
        List<WorkoutPlanEntity> workoutPlanEntities = repository.findAllByUser(user);
        List<WorkoutPlanFormBody> bodyList = new ArrayList<>();
        for (WorkoutPlanEntity plan : workoutPlanEntities){
            WorkoutPlanFormBody body = new WorkoutPlanFormBody();
            body.setExercises(plan.getExercises().split(", "));
            body.setRepetitions(plan.getRepetitions().split(", "));
            body.setName(plan.getName());
            body.setId(plan.getId());
            bodyList.add(body);
        }
        return bodyList;
    }

    @Override
    public void createWorkoutPlan(WorkoutPlanFormBody body) {
        UserEntity user = userRepository.findById(body.getUserId()).get();
        WorkoutPlanEntity workoutPlanEntity = new WorkoutPlanEntity();
        workoutPlanEntity.setName(body.getName());
        workoutPlanEntity.setExercises(Arrays.toString(body.getExercises()).replace("[", "").replace("]", ""));
        workoutPlanEntity.setRepetitions(Arrays.toString(body.getRepetitions()).replace("[", "").replace("]", ""));
        workoutPlanEntity.setUser(user);
        repository.save(workoutPlanEntity);
    }

    @Override
    @Transactional
    public void deleteWorkoutPlanById(UUID planId) {
        repository.deleteById(planId);
    }

    @Override
    public WorkoutPlanFormBody getWorkoutPlanById(UUID workoutPlanId) {
        WorkoutPlanEntity workoutPlan = repository.findById(workoutPlanId).get();
        WorkoutPlanFormBody body = new WorkoutPlanFormBody();
        body.setUserId(workoutPlan.getUser().getId());
        body.setExercises(workoutPlan.getExercises().split(", "));
        return body;
    }
}
