package model;

import java.util.List;

public interface IWorkout {
    void addExercise(IExercise exercise);

    void removeExercise(IExercise exercise);

    void deleteExercise(IExercise exercise);

    void editExercise(IExercise previousExercise, IExercise newExercise);

    void printWorkout();

    List<IExercise> getExerciseList();
}
