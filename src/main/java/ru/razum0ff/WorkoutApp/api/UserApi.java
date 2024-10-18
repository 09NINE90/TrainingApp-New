package ru.razum0ff.WorkoutApp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.dto.UserDTO;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.enumiration.MailType;
import ru.razum0ff.WorkoutApp.mapper.UserMapper;
import ru.razum0ff.WorkoutApp.service.MailService;
import ru.razum0ff.WorkoutApp.service.UserService;

import java.util.Properties;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {
    UserService userService;
    UserMapper mapper;
    MailService mailService;

    @Autowired
    public UserApi(UserService userService, UserMapper mapper, MailService mailService) {
        this.userService = userService;
        this.mapper = mapper;
        this.mailService = mailService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<Void> userSave(Authentication authentication, @RequestBody UserDTO user){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (userDetails.getRole().equals("ROLE_COACH")){
            user.setTrainerId(userDetails.getId());
            user.setRoles("ROLE_USER");
        }else if (userDetails.getRole().equals("ROLE_ADMIN")){
            user.setTrainerId(userDetails.getId());
            user.setRoles("ROLE_COACH");
        }
//        user.setRoles("ROLE_ADMIN");
        UserEntity userToSave = mapper.convertToEntity(user);
        userService.createUser(userToSave);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/user/mainPage").build();
    }

    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/getAuthUser")
    public String getAuthUser(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.getAuthUser(userDetails);
    }

    @GetMapping("/getUsers")
    public String getUsers(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.getUsersByTrainerId(userDetails.getId());
    }

    @GetMapping("/getSignupForm")
    public ModelAndView getSignupForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup-form");
        return modelAndView;
    }

    @GetMapping("/getUserPage/{id}")
    public ModelAndView getUserPage(@PathVariable("id") String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-page");
        modelAndView.addObject(id);
        return modelAndView;
    }

    @GetMapping("/getUserById/{userId}")
    public String getUserById(@PathVariable("userId") UUID id){
        return userService.getJSONUserById(id);
    }
}
