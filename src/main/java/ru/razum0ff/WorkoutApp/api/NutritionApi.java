package ru.razum0ff.WorkoutApp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.entity.NutritionReportEntity;
import ru.razum0ff.WorkoutApp.entity.WeekNutritionEntity;
import ru.razum0ff.WorkoutApp.service.NutritionReportService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class NutritionApi {

    NutritionReportService nutritionReportService;

    @Autowired
    public NutritionApi(NutritionReportService nutritionReportService) {
        this.nutritionReportService = nutritionReportService;
    }

    @GetMapping("/getNutritionReportPage")
    public ModelAndView getNutritionReportPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("nutrition-report");
        return modelAndView;
    }
    @GetMapping("/getNutritionReportPageByUser/{userId}")
    public ModelAndView getNutritionReportPage(@PathVariable("userId")UUID userId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("nutrition-report");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
    @PostMapping("/addNutritionReport")
    public ResponseEntity<Void>  addNutritionReport(Authentication authentication, @RequestBody NutritionReportEntity nutritionReport){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        nutritionReportService.addNutritionReport(userDetails.getId(), nutritionReport);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/user/getNutritionReportPage").build();
    }

    @GetMapping("/getLastWeekNutritionReportByUser/{userId}")
    public WeekNutritionEntity getLastWeekNutritionReportByUser(@PathVariable("userId")UUID userId){
        return nutritionReportService.getLastWeekNutritionReportByUser(userId);
    }


    @GetMapping("/getNutritionReportByUser/{userId}")
    public List<NutritionReportEntity> getNutritionReportByUser(@PathVariable("userId")UUID userId){
        return nutritionReportService.getNutritionReportByUser(userId);
    }
}
