package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.UserPhysicalParametersEntity;

import java.util.UUID;

public interface UserPhysicalParametersService {
    void saveUserPhysicalParameters(UserPhysicalParametersEntity userPhysicalParametersEntity);
//    void deleteUserPhysicalParameters(UserEntity id);

    String getPhysicalParametersByUser(UserEntity id);

    void addPhysicalParameters(UserPhysicalParametersEntity parameters);

    void deletePhysicalParametersById(UUID parametersId);
}
