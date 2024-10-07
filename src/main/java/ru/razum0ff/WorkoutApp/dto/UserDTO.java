package ru.razum0ff.WorkoutApp.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID trainerId;
    private String firstName;
    private String lastName;
    private String roles;
    private String phoneNumber;
    private String username;
    private String password;
}
