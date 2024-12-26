package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a routine, which is a collection of workouts.
 */
public class Routine implements IRoutine {

    private String name;
    private final List<IWorkout> routine;

    public Routine(String name) {
        checkRoutineNameValid(name);
        this.name = name;
        this.routine = new ArrayList<>();
    }

    @Override
    public IRoutine createRoutine(String name) {
        checkRoutineNameValid(name);
        return new Routine(name);
    }

    @Override
    public void addWorkoutToRoutine(IWorkout workout) {
        checkWorkoutAddedToRoutineIsValid(workout);
        routine.add(workout);
    }

    @Override
    public void removeWorkoutFromRoutine(IWorkout workout) {
        if (!routine.contains(workout)) {
            throw new IllegalArgumentException("The workout \"" + workout.getWorkoutName() + "\" is not in the routine.");
        }
        routine.remove(workout);
    }

    @Override
    public void deleteRoutine() {
        if (routine.isEmpty()) {
            throw new IllegalStateException("Cannot delete an already empty routine.");
        }
        routine.clear();
    }

    @Override
    public void editRoutine(int oldIndex, int newIndex) {
        checkRoutineSizeValidForEdit();

        if (oldIndex == newIndex) {
            throw new IllegalArgumentException("Old index and new index cannot be the same.");
        }
        if (oldIndex >= 0 && oldIndex < routine.size() && newIndex >= 0 && newIndex < routine.size()) {
            IWorkout workout = routine.remove(oldIndex);
            routine.add(newIndex, workout);
        } else {
            throw new IndexOutOfBoundsException("Invalid indices for reordering workouts.");
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
        return Collections.unmodifiableList(routine);
    }

    @Override
    public void printRoutine() {
        if (routine.isEmpty()) {
            System.out.println("\nRoutine \"" + name + "\":\nNo workouts in this routine.");
            return;
        }

        System.out.println("\nRoutine \"" + name + "\":");

        for (int i = 0; i < routine.size(); i++) {
            System.out.print((i + 1) + ". ");
            routine.get(i).printWorkout();
            System.out.println();
        }
    }


    /**
     * Creates a deep copy of the routine.
     * @return a new Routine instance with the same workouts.
     */
    public Routine copyRoutine() {
        Routine copy = new Routine(this.name);
        for (IWorkout workout : this.routine) {
            // Assuming Workout has a copy method
            copy.addWorkoutToRoutine(workout);
        }
        return copy;
    }

    // Private Helper Methods

    private void checkRoutineNameValid(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Routine name cannot be null or empty.");
        }
    }

    private void checkRoutineSizeValidForEdit() {
        if (routine.size() < 2) {
            throw new IllegalStateException("A routine must have at least two workouts to be editable.");
        }
    }

    private void checkWorkoutAddedToRoutineIsValid(IWorkout workout) {
        if (workout == null) {
            throw new IllegalArgumentException("Cannot add null workout to routine.");
        }
        if (routine.contains(workout)) {
            throw new IllegalArgumentException("The workout \"" + workout.getWorkoutName() + "\" already exists in the routine.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Routine other = (Routine) obj;
        return name.equals(other.name) && routine.equals(other.routine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, routine);
    }
}
