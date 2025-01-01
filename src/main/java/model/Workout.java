package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Implementation of the IWorkout interface which represents a workout. A workout is composed of the following:
 * 1. A name.
 * 2. At least one exercise.
 */
public class Workout implements IWorkout {

    // the name of the workout.
    private String name;

    // a list to hold the current/active exercises of the workout.
    private final List<IExercise> currentExercises;

    // a list to hold the inactive/deleted exercises of the workout.
    private final List<IExercise> deletedExercises;

    // Default workout constructor.
    public Workout(String name) {
        this.name = name;
        this.currentExercises = new ArrayList<>();
        this.deletedExercises = new ArrayList<>();

        checkWorkoutNameIsValid();
    }

    /**
     * Adds a new exercise to the workout.
     * @param exercise the exercise to add to this workout.
     */
    @Override
    public void addExercise(IExercise exercise) {
        checkExerciseIsNotNull(exercise);
        checkAddExerciseRejectsDuplicates(exercise);

        if (deletedExercises.contains(exercise)) {
            restoreExercise(exercise); //TODO WRITE TEST FOR THIS PART OF LOGIC.
            System.out.println("Exercise \"" + exercise.getName() + "\" restored to workout \"" + name + "\".");
        } else {
            this.currentExercises.add(exercise);
            System.out.println("Exercise \"" + exercise.getName() + "\" added to workout \"" + name + "\".");
        }
    }

    /**
     * Removes an exercise from the workout.
     * @param exercise the given exercise to remove from this workout.
     */
    @Override
    public void removeExercise(IExercise exercise) {
        checkExerciseIsNotNull(exercise);

        if (deletedExercises.contains(exercise)) {
            throw new IllegalArgumentException("The exercise \"" + exercise.getName() + "\" in workout \"" + name + "\" has already been removed.");
        }

        if (currentExercises.contains(exercise)) {
            currentExercises.remove(exercise);
            deletedExercises.add(exercise);
            System.out.println("Exercise \"" + exercise.getName() + "\" removed from workout \"" + name + "\".");
            validateWorkoutHasAtLeastOneExercise(); // Validate after removal
        } else {
            throw new IllegalArgumentException("The exercise \"" + exercise.getName() + "\" does not exist in the current exercises list of workout \"" + name + "\".");
        }
    }

    /**
     * Edits an exercise in this workout.
     * @param currentExercise the current exercise to be edited.
     * @param newExercise the new exercise with updated information after edit.
     */
    @Override
    public void editExercise(IExercise currentExercise, IExercise newExercise) {
        checkExerciseIsNotNull(currentExercise);
        checkExerciseIsNotNull(newExercise);
        checkExerciseEditIsDifferent(currentExercise, newExercise);

        int currentExerciseIndex = currentExercises.indexOf(currentExercise);

        if (currentExerciseIndex != -1) {
            currentExercises.set(currentExerciseIndex, newExercise);
            System.out.println("Exercise \"" + currentExercise.getName() + "\" updated to \"" + newExercise.getName() + "\" in workout \"" + name + "\".");
        } else {
            throw new IllegalArgumentException("The exercise \"" + currentExercise.getName() + "\" was not found in workout \"" + name + "\".");
        }
    }

    /**
     * Restores a previously deleted exercise into this workout.
     * @param exercise the exercise to be restored.
     */
    @Override
    public void restoreExercise(IExercise exercise) {
        if (deletedExercises.contains(exercise)) {
            deletedExercises.remove(exercise);
            currentExercises.add(exercise);
            System.out.println("Exercise \"" + exercise.getName() + "\" restored to workout \"" + name + "\".");
        } else {
            throw new IllegalArgumentException("The exercise \"" + exercise.getName() + "\" is not in the deleted exercises list for workout \"" + name + "\".");
        }
    }

    /**
     * Prints this workout in the following format:
     * Workout name:
     * 1. exercise1 printed in the printExercise format.
     * 2. (repeat 1 for all exercises in the workout).
     *
     * If the currentExercise list is empty, it will say there are no exercises in the workout.
     */
    @Override
    public void printWorkout() {
        if (currentExercises.isEmpty()) {
            System.out.println("\n" + name + ":\nNo exercises in this workout.");
            return;
        }

        System.out.println("\n" + name + ":");
        for (int i = 0; i < currentExercises.size(); i++) {
            System.out.print((i + 1) + ". ");
            currentExercises.get(i).printExercise(); // Delegate printing to `printExercise`
        }
    }

    /**
     * Gets the current name of this workout.
     * @return this workout's name.
     */
    @Override
    public String getWorkoutName() {
        return this.name;
    }

    /**
     * Sets a new name for this workout.
     * @param newName the new name to be given to this workout.
     */
    @Override
    public void setWorkoutName(String newName) {

        if(newName == null || newName.isEmpty()) {
            throw new IllegalArgumentException("Workout name cannot be null or empty stirng. Please choose a valid name");
        }

        System.out.println("Workout name changed from \"" + this.name + "\" to \"" + newName + "\".");
        this.name = newName;
    }

    /**
     * Gets the list of exercises for this workout.
     * @return the exercises of this workout, as a list.
     */
    @Override
    public List<IExercise> getExerciseList() {
        return this.currentExercises;
    }


    /**
     * Obtains the current list of deleted exercises as an unmodifiable list.
     * @return the current list of deleted exercises.
     */
    public List<IExercise> getDeletedExercises() {
        return Collections.unmodifiableList(deletedExercises);
    }

    /**
     * Obtains the list of current exercises as an unmodifiable list.
     * @return the current list of non-deleted exercises.
     */
    public List<IExercise> getCurrentExercises() {
        return Collections.unmodifiableList(currentExercises);
    }

    /**
     * Generates and returns a unique hashcode to each object.
     * @return the hashcode of the object.
     */
    @Override
    public int hashcode() {
        int result = name.hashCode();
        for (IExercise exercise : getCurrentExercises()) {
            result = 31 * result + exercise.hashcode(); // Leverage Exercise.hashCode()
        }
        return result;
    }

    /**
     * Overriden equals method which check if this object is equal to @param object.
     * @param object the other object to compare with.
     * @return tru if this and other object are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Workout other = (Workout) object;
        return this.name.equals(other.name) && this.getCurrentExercises().equals(other.getCurrentExercises());

    }

    // Private helper methods.

    /**
     * Verifies that an edited exercise is different that its current version.
     * @param currentExercise the current exercise.
     * @param newExercise the new exercise.
     */
    private void checkExerciseEditIsDifferent(IExercise currentExercise, IExercise newExercise) {
        if (currentExercise == null || newExercise == null) {
            throw new IllegalArgumentException("Both exercises must be non-null to perform an edit in workout \"" + name + "\".");
        } else if (currentExercise.equals(newExercise)) {
            throw new IllegalStateException("The new exercise must differ from the previous exercise in at least one capacity for editing in workout \"" + name + "\".");
        }
    }

    /**
     * Verifies that no duplicate exercises can be added to the currentExercise list.
     * @param exercise the exercise to add.
     */
    private void checkAddExerciseRejectsDuplicates(IExercise exercise) {
        if (currentExercises.contains(exercise)) {
            throw new IllegalStateException("Cannot add duplicate exercise \"" + exercise.getName() + "\" to workout \"" + name + "\".");
        }
    }

    /**
     * Verifies that no null exercises can be passed into the system
     * @param exercise the exercise to check.
     */
    private void checkExerciseIsNotNull(IExercise exercise) {
        if (exercise == null) {
            throw new IllegalArgumentException("Cannot add, modify, or delete a null exercise in workout \"" + name + "\".");
        }
    }

    /**
     * Validates that the workout contains at least one exercise.
     */
    private void validateWorkoutHasAtLeastOneExercise() {
        if (currentExercises.isEmpty()) {
            throw new IllegalStateException("A workout must contain at least one exercise.");
        }
    }

    private void checkWorkoutNameIsValid() {
        if(this.name == null || this.name.isEmpty()) {
            throw new IllegalArgumentException("Workout name cannot be valid or null");
        }
    }
}
