package ru.razum0ff.WorkoutApp.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.mapper.UserMapper;
import ru.razum0ff.WorkoutApp.repository.UserRepository;
import ru.razum0ff.WorkoutApp.service.UserService;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void createUser(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID());
        repository.save(user);
    }

    @Override
    public UserEntity getUserByEmail(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserEntity getUserById(UUID id) {
        UserEntity user = repository.findById(id).get();
        return user;
    }

    public String getAuthUser(CustomUserDetails user) {
        JsonObject userObject = new JsonObject();
        userObject.addProperty("firstName", user.getFirstName());
        userObject.addProperty("lastName", user.getLastName());
        userObject.addProperty("email", user.getUsername());
        userObject.addProperty("role", user.getRole());
        userObject.addProperty("id", String.valueOf(user.getId()));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(userObject);
    }

    @Override
    public String getUsersByTrainerId(UUID id) {
        Set<UserEntity> users = repository.findByTrainerId(id);
        JsonArray arrayUsersByTrainerId = new JsonArray();
        for (UserEntity user : users) {
            JsonObject objectUser = new JsonObject();
            objectUser.addProperty("name", user.getLastName() + " " + user.getFirstName());
            objectUser.addProperty("email", user.getUsername());
            objectUser.addProperty("phoneNumber", user.getPhoneNumber());
            objectUser.addProperty("id", user.getId().toString());
            arrayUsersByTrainerId.add(objectUser);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(arrayUsersByTrainerId);
    }

    @Override
    public String getJSONUserById(UUID id) {
        UserEntity user = repository.findById(id).get();
        JsonObject objectUser = new JsonObject();
        objectUser.addProperty("name", user.getLastName() + " " + user.getFirstName());
        objectUser.addProperty("email", user.getUsername());
        objectUser.addProperty("phoneNumber", user.getPhoneNumber());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(objectUser);
    }
}
