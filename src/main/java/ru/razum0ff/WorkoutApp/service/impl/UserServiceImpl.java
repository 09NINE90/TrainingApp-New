package ru.razum0ff.WorkoutApp.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.razum0ff.WorkoutApp.dto.CustomUserDetails;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.mapper.UserMapper;
import ru.razum0ff.WorkoutApp.repository.UserRepository;
import ru.razum0ff.WorkoutApp.service.UserService;

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
        UserEntity userToSave = user;
        repository.save(userToSave);
    }

    @Override
    public UserEntity getUserByEmail(String username) {
        return repository.findByUsername(username);
    }

    public String getAuthUser(CustomUserDetails user){
        JsonObject userObject = new JsonObject();
        userObject.addProperty("firstName", user.getFirstName());
        userObject.addProperty("lastName", user.getLastName());
        userObject.addProperty("email", user.getUsername());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(userObject);
    }
}
