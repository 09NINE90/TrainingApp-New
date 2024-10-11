package ru.razum0ff.WorkoutApp.dto.body;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
@Data
public class ReportOfWorkoutBody {
    private UUID id;
    private String name;
    private String[] exercises;
    private String[] reports;
    private LocalDate date;
    private UUID userId;
}
