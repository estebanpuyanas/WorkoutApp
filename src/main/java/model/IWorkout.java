package model;

import java.util.List;

public interface IWorkout {
    void addExercise(IExercise exercise);

    void removeExercise(IExercise exercise);

    void editExercise(IExercise previousExercise, IExercise newExercise);

   void restoreExercise(IExercise exercise);

    void printWorkout();

    String getWorkoutName();

    void setWorkoutName(String newName);

    List<IExercise> getExerciseList();
}
