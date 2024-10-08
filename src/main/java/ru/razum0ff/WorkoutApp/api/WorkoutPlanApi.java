package ru.razum0ff.WorkoutApp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.service.WorkoutPlanService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class WorkoutPlanApi {
    WorkoutPlanService workoutPlanService;

    @Autowired
    public WorkoutPlanApi(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping("/getWorkoutPlan")
    public String getWorkoutPlan(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return workoutPlanService.getWorkoutPlan(userDetails.getId());
    }
    @GetMapping("/user/getWorkoutPlanPage")
    public ModelAndView getWorkoutPlanPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("workout-plan");
        return modelAndView;
    }

    @GetMapping("/user/getWorkoutPlanForm")
    public ModelAndView getWorkoutPlanForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("workout-plan-form");
        return modelAndView;
    }
}
