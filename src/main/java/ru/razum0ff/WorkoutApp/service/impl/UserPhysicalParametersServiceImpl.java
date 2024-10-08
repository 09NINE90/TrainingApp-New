package ru.razum0ff.WorkoutApp.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.UserPhysicalParameters;
import ru.razum0ff.WorkoutApp.repository.UserPhysicalParametersRepository;
import ru.razum0ff.WorkoutApp.service.UserPhysicalParametersService;

import java.util.List;

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

    @Override
    public String getMyPhysicalParameters(UserEntity user) {
        List<UserPhysicalParameters> list = repository.findAllByUserOrderByDateDesc(user);
        JsonArray arrayParameters = new JsonArray();
        for(UserPhysicalParameters parameters : list){
            JsonObject parameter = new JsonObject();
            parameter.addProperty("age", parameters.getAge());
            parameter.addProperty("weight", parameters.getWeight());
            parameter.addProperty("armCircumference", parameters.getArmCircumference());
            parameter.addProperty("legGirth", parameters.getLegGirth());
            parameter.addProperty("chestCircumference", parameters.getChestCircumference());
            parameter.addProperty("hipCircumference", parameters.getHipCircumference());
            parameter.addProperty("waistCircumference", parameters.getWaistCircumference());
            parameter.addProperty("date", parameters.getDate().toString());
            arrayParameters.add(parameter);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(arrayParameters);
    }

    @Override
    @Transactional
    public void addPhysicalParameters(UserPhysicalParameters parameters) {
        repository.save(parameters);
    }

//    @Override
//    @Transactional
//    public void deleteUserPhysicalParameters(UserEntity user) {
//        repository.deleteByUser(user);
//    }
}
