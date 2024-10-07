package ru.razum0ff.WorkoutApp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.dto.UserDTO;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.service.UserService;

import java.util.Properties;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {
    UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String userCreate(Model model){
        model.addAttribute("user",new UserDTO());
        return "signup";
    }
    @PostMapping("/create")
    public String userSave(Authentication authentication, @ModelAttribute("user") UserEntity user, Model model){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (userDetails.getRole().equals("ROLE_COACH")){
            user.setTrainerId(userDetails.getId());
            user.setRoles("ROLE_USER");
        }else if (userDetails.getRole().equals("ROLE_ADMIN")){
            user.setTrainerId(userDetails.getId());
            user.setRoles("ROLE_COACH");
        }
//        user.setRoles("ROLE_ADMIN");
//        mailService.sendMail(user, MailType.REGISTRATION, new Properties());
        userService.createUser(user);
        return "redirect:/api/v1/home";
    }
}
