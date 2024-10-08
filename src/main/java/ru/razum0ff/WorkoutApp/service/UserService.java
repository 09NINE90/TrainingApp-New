package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.dto.UserDTO;
import ru.razum0ff.WorkoutApp.entity.UserEntity;

import java.util.UUID;

public interface UserService {

    void createUser(UserEntity user);
    UserEntity getUserByEmail(String email);
    UserEntity getUserById(UUID id);
    String getAuthUser(CustomUserDetails user);
    String getUsersByTrainerId(UUID id);
}
