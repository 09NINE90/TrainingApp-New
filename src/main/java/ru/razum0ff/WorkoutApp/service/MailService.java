package ru.razum0ff.WorkoutApp.service;

import ru.razum0ff.WorkoutApp.entity.UserEntity;
import ru.razum0ff.WorkoutApp.entity.enumiration.MailType;

import java.util.Properties;

public interface MailService {
    void sendMail(UserEntity user, MailType type, Properties properties);
}
