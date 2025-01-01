package model;

import java.util.List;

/**
 * Interface that represents a workout. A workout is composed of the following:
 * 1. A name.
 * 2. A list of at least one exercise.
 */
public interface IWorkout {

    /**
     * Adds a new exercise to the workout.
     * @param exercise the exercise to add to this workout.
     */
    void addExercise(IExercise exercise);

    /**
     * Removes an exercise from the workout.
     * @param exercise the given exercise to remove from this workout.
     */
    void removeExercise(IExercise exercise);

    /**
     * Edits an exercise in this workout.
     * @param currentExercise the current exercise to be edited.
     * @param newExercise the new exercise with updated information after edit.
     */
    void editExercise(IExercise currentExercise, IExercise newExercise);

    /**
     * Restores a previously deleted exercise into this workout.
     * @param exercise the exercise to be restored.
     */
   void restoreExercise(IExercise exercise);

    /**
     * Prints this workout.
     */
    void printWorkout();

    /**
     * Gets the current name of this workout.
     * @return this workout's name.
     */
    String getWorkoutName();

    /**
     * Sets a new name for this workout.
     * @param newName the new name to be given to this workout.
     */
    void setWorkoutName(String newName);

    /**
     * Gets the list of exercises for this workout.
     * @return the exercises of this workout, as a list.
     */
    List<IExercise> getExerciseList();

    /**
     * Overriden hashcode method which generates and returns a unique hashcode to each object.
     * @return the hashcode of the object.
     */
    int hashcode();


    /**
     * Overriden equals method which check if this object is equal to @param object.
     * @param object the other object to compare with.
     * @return tru if this and other object are equal, false otherwise.
     */
    boolean equals(Object object);
}
