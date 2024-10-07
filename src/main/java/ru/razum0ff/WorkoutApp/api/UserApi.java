package ru.razum0ff.WorkoutApp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.dto.UserDTO;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.mapper.UserMapper;
import ru.razum0ff.WorkoutApp.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {
    UserService userService;
    UserMapper mapper;

    @Autowired
    public UserApi(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/createUser")
    public String userSave(Authentication authentication, @RequestBody UserDTO user){
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
        UserEntity userToSave = mapper.convertToEntity(user);
        userService.createUser(userToSave);
        return "OK";
    }
}
