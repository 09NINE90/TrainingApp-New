package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.UserPhysicalParameters;

import java.util.UUID;

public interface UserPhysicalParametersService {
    void saveUserPhysicalParameters(UserPhysicalParameters userPhysicalParameters);
//    void deleteUserPhysicalParameters(UserEntity id);
}
