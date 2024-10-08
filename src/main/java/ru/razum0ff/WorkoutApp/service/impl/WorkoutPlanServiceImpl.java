package ru.razum0ff.WorkoutApp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.WorkoutPlan;
import ru.razum0ff.WorkoutApp.repository.UserRepository;
import ru.razum0ff.WorkoutApp.repository.WorkoutPlanRepository;
import ru.razum0ff.WorkoutApp.service.UserService;
import ru.razum0ff.WorkoutApp.service.WorkoutPlanService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutPlanServiceImpl implements WorkoutPlanService {
    WorkoutPlanRepository repository;
    UserRepository userRepository;

    @Override
    public String getWorkoutPlan(UUID uuid) {
        UserEntity user = userRepository.findById(uuid).get();
        List<WorkoutPlan> workoutPlans = repository.findAllByUser(user);

        return null;
    }
}
