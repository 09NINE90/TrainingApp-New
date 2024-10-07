package ru.razum0ff.WorkoutApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.UserPhysicalParameters;
import ru.razum0ff.WorkoutApp.repository.UserPhysicalParametersRepository;
import ru.razum0ff.WorkoutApp.service.UserPhysicalParametersService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPhysicalParametersServiceImpl implements UserPhysicalParametersService {
    UserPhysicalParametersRepository repository;

    @Autowired
    public UserPhysicalParametersServiceImpl(UserPhysicalParametersRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void saveUserPhysicalParameters(UserPhysicalParameters userPhysicalParameters) {
        repository.save(userPhysicalParameters);
    }

//    @Override
//    @Transactional
//    public void deleteUserPhysicalParameters(UserEntity user) {
//        repository.deleteByUser(user);
//    }
}
