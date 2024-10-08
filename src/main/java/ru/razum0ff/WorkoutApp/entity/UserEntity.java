package ru.razum0ff.WorkoutApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "workout_app", name = "user")
public class UserEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "trainer_id")
    private UUID trainerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "roles")
    private String roles;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Override
    public String toString(){
        return "id: " + this.id + " firstName: " + this.firstName + " lastName: " + this.lastName +
                " roles: " + this.roles + " phoneNumber: " + this.phoneNumber +
                " username: " + this.username;
    }
}
