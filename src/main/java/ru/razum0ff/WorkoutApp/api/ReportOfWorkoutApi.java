package ru.razum0ff.WorkoutApp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.dto.body.ReportOfWorkoutBody;
import ru.razum0ff.WorkoutApp.service.ReportOfWorkoutService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ReportOfWorkoutApi {
    ReportOfWorkoutService reportOfWorkoutService;

    @Autowired
    public ReportOfWorkoutApi(ReportOfWorkoutService reportOfWorkoutService) {
        this.reportOfWorkoutService = reportOfWorkoutService;
    }
    @GetMapping("/getReportOfWorkoutPage")
    public ModelAndView getReportOfWorkoutPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("report-of-workout");
        return modelAndView;
    }
    @GetMapping("/getReportOfWorkoutPageByUserId/{userId}")
    public ModelAndView getReportOfWorkoutPageByUserId(@PathVariable("userId")UUID userId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("report-of-workout");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
    @GetMapping("/getReportOfWorkoutForm/{workoutPlanId}")
    public ModelAndView getReportOfWorkoutForm(@PathVariable("workoutPlanId")UUID workoutPlanId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("report-of-workout-form");
        modelAndView.addObject("workoutPlanId", workoutPlanId);
        return modelAndView;
    }
    @GetMapping("/getEditReportOfWorkoutForm/{reportOfWorkoutId}")
    public ModelAndView getEditReportOfWorkoutForm(@PathVariable("reportOfWorkoutId")UUID reportOfWorkoutId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("report-of-workout-edit-form");
        modelAndView.addObject("reportOfWorkoutId", reportOfWorkoutId);
        return modelAndView;
    }
    @PostMapping("/saveReportOfWorkout")
    public ResponseEntity<Void> saveReportOfWorkout(Authentication authentication, @RequestBody ReportOfWorkoutBody report){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        reportOfWorkoutService.saveReportOfWorkout(userDetails, report);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/user/getReportOfWorkoutPage").build();
    }
    @GetMapping("/getReportOfWorkoutByUser/{userId}")
    public List<ReportOfWorkoutBody> getReportOfWorkoutByUser(@PathVariable("userId") UUID userId){
        return reportOfWorkoutService.getReportOfWorkoutByUser(userId);
    }
    @GetMapping("/getReportOfWorkoutById/{reportOfWorkoutId}")
    public ReportOfWorkoutBody getReportOfWorkoutById(@PathVariable("reportOfWorkoutId")UUID reportOfWorkoutId){
        return reportOfWorkoutService.getReportOfWorkoutById(reportOfWorkoutId);
    }
    @PostMapping("/updateReportOfWorkout/{reportOfWorkoutId}")
    public ResponseEntity<Void> updateReportOfWorkout(@PathVariable("reportOfWorkoutId")UUID reportOfWorkoutId, @RequestBody ReportOfWorkoutBody report){
        reportOfWorkoutService.updateReportOfWorkoutById(reportOfWorkoutId, report);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/user/getReportOfWorkoutPage").build();
    }
}
