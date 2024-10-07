package ru.razum0ff.WorkoutApp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.mapper.UserMapper;
import ru.razum0ff.WorkoutApp.repository.UserRepository;
import ru.razum0ff.WorkoutApp.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void createUser(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        UserEntity userToSave = user;
        userRepository.save(userToSave);
    }

    @Override
    public UserEntity getUserByEmail(String username) {
        return userRepository.findByUsername(username);
    }
}
