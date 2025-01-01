package model;

import java.util.List;

public interface IRoutine {
    IRoutine createRoutine(String name);

    void addWorkoutToRoutine(IWorkout workout);

    void removeWorkoutFromRoutine(IWorkout workout);

    void deleteRoutine();

    void editRoutine(int oldIndex, int newIndex);

    void restoreWorkoutToRoutine(IWorkout workout);

    String getRoutineName();

    void setRoutineName(String newName);

    List<IWorkout> getWorkouts();

    void printRoutine();

    int hashcode();

    boolean equals(Object object);
}
