package ru.razum0ff.WorkoutApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(schema = "workout_app", name = "workout_plan")
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String exercises;
    private String repetitions;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}