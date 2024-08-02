package model;

import java.time.LocalDate;
import java.util.List;

public interface IWorkout {
    String getWorkoutID();
    void setWorkoutID(String workoutID);
    String getPlanID();
    void setPlanID(String planID);
    LocalDate getDate();
    void setDate(LocalDate date);
    List<IExercise> getExercises();
    void setExercises(List<IExercise> exercises);
}
