package ru.razum0ff.WorkoutApp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.dto.body.WorkoutPlanFormBody;
import ru.razum0ff.WorkoutApp.service.WorkoutPlanService;

import java.util.List;
import java.util.UUID;

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
    public List<WorkoutPlanFormBody> getWorkoutPlan(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return workoutPlanService.getWorkoutPlanByUser(userDetails.getId());
    }
    @GetMapping("/getWorkoutPlanById/{workoutPlanId}")
    private WorkoutPlanFormBody getWorkoutPlanById(@PathVariable("workoutPlanId") UUID workoutPlanId){
        return workoutPlanService.getWorkoutPlanById(workoutPlanId);
    }
    @GetMapping("/getWorkoutPlanByUser/{userId}")
    public List<WorkoutPlanFormBody> getWorkoutPlanByUser(@PathVariable("userId")UUID userId){
        return workoutPlanService.getWorkoutPlanByUser(userId);
    }
    @GetMapping("/getWorkoutPlanPage")
    public ModelAndView getWorkoutPlanPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("workout-plan");
        return modelAndView;
    }
    @GetMapping("/getWorkoutPlanPageByUserId/{userId}")
    public ModelAndView getWorkoutPlanPageByUserId(@PathVariable("userId")UUID userId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("workout-plan");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
    @GetMapping("/getWorkoutPlanForm/{userId}")
    public ModelAndView getWorkoutPlanForm(@PathVariable("userId")UUID userId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("workout-plan-form");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
    @PostMapping("/createWorkoutPlan")
    public ResponseEntity<Void> createWorkoutPlan(@RequestBody WorkoutPlanFormBody body){
        workoutPlanService.createWorkoutPlan(body);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/user/getWorkoutPlanPageByUserId/"+body.getId()).build();
    }
    @DeleteMapping("/deleteWorkoutPlanById/{planId}")
    public ResponseEntity<Void> deleteWorkoutPlanById(@PathVariable("planId")UUID planId){
        try {
            workoutPlanService.deleteWorkoutPlanById(planId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
