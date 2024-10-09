package ru.razum0ff.WorkoutApp.dto.body;

import lombok.Data;

import java.util.Arrays;
import java.util.UUID;

@Data
public class WorkoutPlanFormBody {
    private UUID id;
    private String name;
    private String[] exercises;
    private String[] repetitions;
    private UUID userId;

    @Override
    public String toString(){
        return "Название тренировки: " + this.name + "\n" +
                "Список упражнений: " + Arrays.toString(this.exercises) + "\n" +
                "Список повторений: " + Arrays.toString(this.repetitions);
    }
}
