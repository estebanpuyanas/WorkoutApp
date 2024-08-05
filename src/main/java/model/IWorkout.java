package model;

import java.util.List;

public interface IWorkout {
    public void addExercise(IExercise exercise);
    public void removeExercise(IExercise exercise);
    public void editExercise(IExercise previousExercise, IExercise newExercise);
    public void printWorkout();
    public List<IExercise> getExerciseList();
}
