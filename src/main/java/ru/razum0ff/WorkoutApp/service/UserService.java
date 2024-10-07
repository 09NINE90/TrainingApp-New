package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.dto.UserDTO;
import ru.razum0ff.WorkoutApp.entity.UserEntity;

public interface UserService {

    void createUser(UserEntity user);
    UserEntity getUserByEmail(String email);
}
