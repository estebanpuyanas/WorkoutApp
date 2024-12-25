package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Workout implements IWorkout {

    private String name;
    private List<IExercise> currentExercises;
    private List<IExercise> deletedExercises;

    public Workout(String name) {
        this.name = name;
        this.currentExercises = new ArrayList<>();
        this.deletedExercises = new ArrayList<>();
    }

    @Override
    public void addExercise(IExercise exercise) {
        checkExerciseIsNotNull(exercise);
        checkAddExerciseRejectsDuplicates(exercise);

        if (deletedExercises.contains(exercise)) {
            this.deletedExercises.remove(exercise);
            this.currentExercises.add(exercise);
            System.out.println("Exercise \"" + exercise.getName() + "\" restored to workout \"" + name + "\".");
        } else {
            this.currentExercises.add(exercise);
            System.out.println("Exercise \"" + exercise.getName() + "\" added to workout \"" + name + "\".");
        }
    }

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
        } else {
            throw new IllegalArgumentException("The exercise \"" + exercise.getName() + "\" does not exist in the current exercises list of workout \"" + name + "\".");
        }
    }

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

    @Override
    public void printWorkout() {
        if (currentExercises.isEmpty()) {
            System.out.println(name + ":\nNo exercises in this workout.");
            return;
        }

        System.out.println(name + ":\n");
        int count = 1;
        for (IExercise exercise : currentExercises) {
            System.out.print(count + ". ");
            exercise.printExercise();
            count++;
        }
    }

    @Override
    public String getWorkoutName() {
        return this.name;
    }

    @Override
    public void setWorkoutName(String newName) {
        System.out.println("Workout name changed from \"" + this.name + "\" to \"" + newName + "\".");
        this.name = newName;
    }

    @Override
    public List<IExercise> getExerciseList() {
        return this.currentExercises;
    }

    public List<IExercise> getDeletedExercises() {
        return Collections.unmodifiableList(deletedExercises);
    }

    private void checkExerciseEditIsDifferent(IExercise previousExercise, IExercise newExercise) {
        if (previousExercise == null || newExercise == null) {
            throw new IllegalArgumentException("Both exercises must be non-null to perform an edit in workout \"" + name + "\".");
        } else if (previousExercise.equals(newExercise)) {
            throw new IllegalStateException("The new exercise must differ from the previous exercise in at least one capacity for editing in workout \"" + name + "\".");
        }
    }

    private void checkAddExerciseRejectsDuplicates(IExercise exercise) {
        if (currentExercises.contains(exercise)) {
            throw new IllegalStateException("Cannot add duplicate exercise \"" + exercise.getName() + "\" to workout \"" + name + "\".");
        }
    }

    private void checkExerciseIsNotNull(IExercise exercise) {
        if (exercise == null) {
            throw new IllegalArgumentException("Cannot add or modify a null exercise in workout \"" + name + "\".");
        }
    }
}