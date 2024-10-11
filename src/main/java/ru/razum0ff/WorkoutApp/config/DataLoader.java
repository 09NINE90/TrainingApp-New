package ru.razum0ff.WorkoutApp.config;


import org.apache.commons.jexl3.JxltEngine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    final
    UserRepository userRepository;
    final
    PasswordEncoder encoder;
    public DataLoader(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @java.lang.Override
    public void run(java.lang.String... args) throws JxltEngine.Exception {
        UserEntity user = new UserEntity();
        user.setFirstName("Имя");
        user.setLastName("Фамилия");
        user.setUsername("почта");
        user.setPhoneNumber("+7 номер");
        user.setRoles("ROLE_ADMIN");
        user.setPassword(encoder.encode("пароль"));
//        userRepository.save(user);
//        System.out.println("Пользователь добавлен!");
    }
}
