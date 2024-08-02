package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface IProgress {
    String getProgressID();
    void setProgressID(String progressID);
    String getUserID();
    void setUserID();
    LocalDate getDate();
    void setDate(LocalDate date);
    String getExerciseName();
    void setExerciseName(String exerciseName);
    int getReps();
    void setReps(int reps);
    double getWeight();
    void setWeight(double weight);
}
