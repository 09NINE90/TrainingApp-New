package ru.razum0ff.WorkoutApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "workout_app", name = "week_nutrition")
public class WeekNutritionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "check_days")
    private String checkDays;

    @Column(name = "count_days_of_week")
    private Short countDaysOfWeek;

    @Column(name = "sum_calories")
    private float sumCalories;

    @Column(name = "sum_carbohydrates")
    private float sumCarbohydrates;

    @Column(name = "sum_proteins")
    private float sumProteins;

    @Column(name = "sum_fats")
    private float sumFats;

    @Column(name = "last_date")
    private Date lastDate;

    @Column(name = "week_start")
    private Date weekStart;

    @Column(name = "week_end")
    private Date weekEnd;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
