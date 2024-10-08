package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.UserPhysicalParameters;

import java.util.List;
import java.util.UUID;

public interface UserPhysicalParametersService {
    void saveUserPhysicalParameters(UserPhysicalParameters userPhysicalParameters);
//    void deleteUserPhysicalParameters(UserEntity id);

    String getMyPhysicalParameters(UserEntity id);

    void addPhysicalParameters(UserPhysicalParameters parameters);
}
