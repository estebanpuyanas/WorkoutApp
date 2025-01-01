package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a routine, which is a collection of workouts.
 */
public class Routine implements IRoutine {

    private String name;
    private final List<IWorkout> currentWorkoutsInRoutine;
    private final List<IWorkout> deleletedWorkoutsInRoutine;

    public Routine(String name) {
        checkRoutineNameValid(name);
        this.name = name;
        this.currentWorkoutsInRoutine = new ArrayList<>();
        this.deleletedWorkoutsInRoutine = new ArrayList<>();
    }

    @Override
    public IRoutine createRoutine(String name) {
        checkRoutineNameValid(name);
        return new Routine(name);
    }

    //TODO ADD PRINTING LOG MESSAGES
    @Override
    public void addWorkoutToRoutine(IWorkout workout) {

        checkWorkoutAddedToRoutineIsValid(workout);
        checkWorkoutIsNotNull(workout);

        if(deleletedWorkoutsInRoutine.contains(workout)) {
            this.deleletedWorkoutsInRoutine.remove(workout);
            this.currentWorkoutsInRoutine.add(workout);
        } else {
            currentWorkoutsInRoutine.add(workout);
        }
    }

    @Override
    public void removeWorkoutFromRoutine(IWorkout workout) {
        checkRoutineSizeValidForDeleteWorkout();
        if (!currentWorkoutsInRoutine.contains(workout)) {
            throw new IllegalArgumentException("The workout \"" + workout.getWorkoutName() + "\" is not in the routine.");
        }
        currentWorkoutsInRoutine.remove(workout);
        deleletedWorkoutsInRoutine.add(workout);
    }

    //TODO CONSIDER WHETHER THIS METHOD SHOULD BE KEPT. (PROBABLY YES).
    @Override
    public void deleteRoutine() {
        if (currentWorkoutsInRoutine.isEmpty()) {
            throw new IllegalStateException("Cannot delete an already empty routine.");
        }
        currentWorkoutsInRoutine.clear();
    }

    //TODO CHECK IF METHOD LOGIC CAN BE IMPROVED.
    @Override
    public void editRoutine(int oldIndex, int newIndex) {
        checkRoutineSizeValidForEdit();

        if (oldIndex == newIndex) {
            throw new IllegalArgumentException("Old index and new index cannot be the same.");
        }
        if (oldIndex >= 0 && oldIndex < currentWorkoutsInRoutine.size() && newIndex >= 0 && newIndex < currentWorkoutsInRoutine.size()) {
            IWorkout workout = currentWorkoutsInRoutine.remove(oldIndex);
            currentWorkoutsInRoutine.add(newIndex, workout);
        } else {
            throw new IndexOutOfBoundsException("Invalid indices for reordering workouts.");
        }
    }

    @Override
    public void restoreWorkoutToRoutine(IWorkout workout) {

        if (deleletedWorkoutsInRoutine.contains(workout)) {
            deleletedWorkoutsInRoutine.remove(workout);
            currentWorkoutsInRoutine.add(workout);
        } else {
            throw new IllegalArgumentException("This workout is not in the deleted workout list. Please try again");
        }
    }

    @Override
    public String getRoutineName() {
        return name;
    }

    @Override
    public void setRoutineName(String newName) {
        checkRoutineNameValid(newName);
        this.name = newName;
    }

    @Override
    public List<IWorkout> getWorkouts() {
        return this.currentWorkoutsInRoutine;
    }

    public List<IWorkout> getDeletedWorkoutsInRoutine() {
        return Collections.unmodifiableList(deleletedWorkoutsInRoutine);
    }

    public List<IWorkout> getCurrentWorkoutsInRoutine() {
        return Collections.unmodifiableList(currentWorkoutsInRoutine);
    }

    //TODO FIX FORMATTING OF THIS METHOD.
    @Override
    public void printRoutine() {
        if (currentWorkoutsInRoutine.isEmpty()) {
            System.out.println("\nRoutine \"" + name + "\":\nNo workouts in this routine.");
            return;
        }

        System.out.println("\nRoutine \"" + name + "\":");

        for (int i = 0; i < currentWorkoutsInRoutine.size(); i++) {
            System.out.print((i + 1) + ". ");
            currentWorkoutsInRoutine.get(i).printWorkout();
            System.out.println();
        }
    }

    @Override
    public int hashcode() {

        int result = name.hashCode();

        for(IWorkout workout : getCurrentWorkoutsInRoutine()) {
            result = 31 * result + workout.hashcode();
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Routine other = (Routine) object;
        return this.name.equals(other.name) && this.getCurrentWorkoutsInRoutine().equals(other.getCurrentWorkoutsInRoutine());
    }

    // Private Helper Methods
    private void checkWorkoutIsNotNull(IWorkout workout) {
        if(workout == null) {
            throw new IllegalArgumentException("Cannot add, modify, or delete a null workout from routine.");
        }
    }


    private void checkRoutineNameValid(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Routine name cannot be null or empty.");
        }
    }

    private void checkRoutineSizeValidForEdit() {
        if (currentWorkoutsInRoutine.size() < 2) {
            throw new IllegalStateException("A routine must have at least two workouts to be editable.");
        }
    }

    private void checkRoutineSizeValidForDeleteWorkout() {
        if(currentWorkoutsInRoutine.size() == 1) {
            throw new UnsupportedOperationException("A routine must have at least one workout");
        }
    }

    private void checkWorkoutAddedToRoutineIsValid(IWorkout workout) {
       checkWorkoutIsNotNull(workout);
        if (currentWorkoutsInRoutine.contains(workout)) {
            throw new IllegalArgumentException("The workout \"" + workout.getWorkoutName() + "\" already exists in the routine.");
        }
    }

    //TODO FIX THIS METHOD TO USE THE OVERRIDEN EQUALS() OF THIS CLASS ONCE IMPLEMENTED.
    private void checkWorkoutEditIsDifferent(IWorkout currentWorkout, IWorkout newWorkout) {
        if (currentWorkout == null || newWorkout == null) {
            throw new IllegalArgumentException("Both exercises must be non-null to perform an edit in workout \"" + name + "\".");
        } else if (currentWorkout.equals(newWorkout)) {
            throw new IllegalStateException("The new exercise must differ from the previous exercise in at least one capacity for editing in workout \"" + name + "\".");
        }
    }
}
