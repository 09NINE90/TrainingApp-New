package ru.razum0ff.WorkoutApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "workout_app", name = "user_phys_parameters")
public class UserPhysicalParametersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private int age; // Возраст
    private double weight; // Вес
    private double armCircumference; //Обхват руки
    private double legGirth; //Обхват ноги
    private double chestCircumference; //Обхват грудной клетки
    private double hipCircumference; //Обхват бедер
    private double waistCircumference; //Обхват талии (под пупком)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date; //Дата обновления параметров

    @Override
    public String toString(){
        return "Пользоваетль: " + this.user.toString() + "Вес: " + this.weight + " Обхват руки: " + this.armCircumference + " Обхват ноги: " + this.legGirth +
                " Обхват грудной клетки: " + this.chestCircumference + " Обхват бедер: " + this.waistCircumference +
                " Дата: " + this.date;
    }
}
