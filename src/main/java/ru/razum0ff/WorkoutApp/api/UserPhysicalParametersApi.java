package ru.razum0ff.WorkoutApp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.UserPhysicalParameters;
import ru.razum0ff.WorkoutApp.service.UserPhysicalParametersService;
import ru.razum0ff.WorkoutApp.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserPhysicalParametersApi {
    UserPhysicalParametersService userPhysicalParametersService;
    UserService userService;

    @Autowired
    public UserPhysicalParametersApi(UserPhysicalParametersService userPhysicalParametersService, UserService userService) {
        this.userPhysicalParametersService = userPhysicalParametersService;
        this.userService = userService;
    }

    @GetMapping("/getMyPhysicalParameters")
    public String getMyPhysicalParameters(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userService.getUserById(userDetails.getId());
        return userPhysicalParametersService.getMyPhysicalParameters(user);
    }

    @PostMapping("/addPhysicalParameters")
    public ResponseEntity<Void> addPhysicalParameters(Authentication authentication, @RequestBody UserPhysicalParameters parameters) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userService.getUserById(userDetails.getId());
        parameters.setUser(user);
        userPhysicalParametersService.addPhysicalParameters(parameters);
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/mainPage").build();
    }
    @GetMapping("/getUserPhysicalParametersFormPage")
    public ModelAndView getUserPhysicalParametersFormPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-physical-parameters-form");
        return modelAndView;
    }

}
