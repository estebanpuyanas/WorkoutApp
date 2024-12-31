package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of IExercise interface that represents an exercise.
 * An exercise is composed of the following parameters:
 * 1. Name
 * 2. Number of sets
 * 3. Repetitions per set.
 * 4. Weight used in exercise.
 * 5. Mode
 */
public class Exercise implements IExercise {

    /**
     * The name of the exercise.
     */
    private String name;

    /**
     * The sets of the exercise.
     */
    private int sets;

    /**
     * List to store SetReps objects representing reps for each set.
     */
    private final List<SetReps> setRepsList;

    /**
     * The target amount of repetitions desired to be achieved in a set.
     */
    private int targetReps;

    /**
     * The weight of the exercise.
     */
    private double weight;

    /**
     * The mode of the exercise.
     */
    private Mode mode;

    /**
     * Default constructor for a new exercise.
     *
     * @param name       the name of the exercise.
     * @param sets       the sets of the exercise.
     * @param setRepsList the reps done per each set of the exercise.
     * @param targetReps the target amount of repetitions desired to be achieved in a set.
     * @param weight     the weight of the exercise.
     * @param mode       the mode of the exercise.
     */
    public Exercise(String name, int sets, List<SetReps> setRepsList, int targetReps, double weight, Mode mode) {
        checkExerciseNameValid(name);
        checkExerciseSetsIsValid(sets);
        checkExerciseWeightValid(weight);

        this.name = name;
        this.sets = sets;
        this.targetReps = targetReps;
        this.weight = weight;
        this.mode = mode;

        // Generate default SetReps if the list is empty
        if (setRepsList == null || setRepsList.isEmpty()) {
            setRepsList = new ArrayList<>();
            for (int i = 1; i <= sets; i++) {
                setRepsList.add(new SetReps(i, 0)); // Default reps = 0
            }
        }

        validateSetRepsList(setRepsList, sets);
        this.setRepsList = new ArrayList<>(setRepsList); // Ensure immutability
    }


    /**
     * Allows for the creation of a new exercise.
     *
     * @param name       the given name.
     * @param sets       the number of sets the exercise will be done for.
     * @param setRepsList the repetitions of the exercise per set.
     * @param targetReps the target amount of repetitions desired to be achieved in a set.
     * @param weight     the weight being used for the exercise.
     * @param mode       the mode of the exercise.
     * @return a new exercise, with the specified parameters.
     */
    @Override
    public Exercise createExercise(String name, int sets, List<SetReps> setRepsList, int targetReps, double weight, Mode mode) {
        return new Exercise(name, sets, setRepsList, targetReps, weight, mode);
    }

    /**
     * Updates the weight being used on this exercise.
     *
     * @param weight the new weight.
     */
    @Override
    public void updateWeight(double weight) {
        checkExerciseWeightValid(weight);
        this.weight = weight;
    }

    /**
     * Obtains the current weight of this exercise.
     *
     * @return the given weight.
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Updates the amount of sets for this exercise.
     *
     * @param sets the new amount of sets.
     */
    @Override
    public void updateSets(int sets) {
        checkExerciseSetsIsValid(sets);
        this.sets = sets;
    }

    /**
     * Gets the current number of sets for this exercise.
     *
     * @return the number of sets.
     */
    @Override
    public int getSets() {
        return this.sets;
    }

    /**
     * Updates the reps for a specific set.
     *
     * @param setIndex the index of the set to update.
     * @param reps     the number of reps to assign to the set.
     */
    @Override
    public void updateReps(int setIndex, int reps) {
        validateSetIndex(setIndex);
        checkUpdateRepsDifferent(setRepsList.get(setIndex).getReps(), reps);
        setRepsList.set(setIndex, new SetReps(setIndex, reps));
    }

    /**
     * Obtains all the SetReps for every set.
     *
     * @return the SetReps list for every set.
     */
    public List<SetReps> getAllSetReps() {
        return Collections.unmodifiableList(setRepsList);
    }

    /**
     * Obtains the reps for the specified set.
     *
     * @param setIndex the specified set.
     * @return the number of reps.
     */
    @Override
    public int getRepsForSpecificSet(int setIndex) {
        validateSetIndex(setIndex);
        return setRepsList.get(setIndex).getReps();
    }

    /**
     * Sets the mode of this exercise.
     *
     * @param mode the mode.
     */
    @Override
    public void updateMode(Mode mode) {
        checkUpdateModeDifferent(getMode(), mode);
        this.mode = mode;
    }

    /**
     * Obtains the mode of this exercise.
     *
     * @return the exercise mode.
     */
    @Override
    public Mode getMode() {
        return this.mode;
    }

    /**
     * Updates the name of this exercise.
     *
     * @param name the new name.
     */
    @Override
    public void updateName(String name) {
        checkExerciseNameValid(name);
        this.name = name;
    }

    /**
     * Gets the name of this exercise.
     *
     * @return the name of the exercise.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the target reps of this exercise.
     * @return the given exercise's target reps.
     */
    @Override
    public int getTargetReps() {
        return this.targetReps;
    }

    /**
     * Updates the target repetitions of this exercise.
     * @param newTargetReps the new number of target reps.
     */
    @Override
    public void updateTargetReps(int newTargetReps) {
        if (newTargetReps == this.targetReps) {
            throw new IllegalArgumentException("New target reps must be different from current target reps");
        } else {
            this.targetReps = newTargetReps;
        }
    }

    /**
     * Prints the current exercise in the following format:
     * ExerciseName sets X targetReps @ weight.
     */
    @Override
    public void printExercise() {
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedWeight = df.format(this.getWeight());

        // Format SetReps list
        StringBuilder repsList = new StringBuilder();
        boolean allRepsAreZero = true;
        for (SetReps setReps : setRepsList) {
            if (setReps.getReps() != 0) {
                allRepsAreZero = false;
            }
            if (repsList.length() > 0) {
                repsList.append(", ");
            }
            repsList.append("Set ").append(setReps.getSetNumber()).append(": ").append(setReps.getReps());
        }

        // Handle empty or all-zero reps list
        String repsOutput = allRepsAreZero ? "[]" : "[" + repsList + "]";

        System.out.println(
                this.getName() + " " +
                        this.getSets() + "x" + this.getTargetReps() + "@" + formattedWeight +
                        " (Reps per set: " + repsOutput + ")"
        );
    }

    /**
     * Overridden hashCode method to ensure consistent hashing for Exercise objects.
     *
     * @return the hashCode of an object.
     */
    @Override
    public int hashcode() {
        int result = name.hashCode();
        result = 31 * result + Integer.hashCode(sets);
        result = 31 * result + Integer.hashCode(targetReps);
        result = 31 * result + Double.hashCode(weight);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + setRepsList.hashCode(); // Include setRepsList in hashCode
        return result;
    }


    /**
     * Overridden equals method to ensure object equality for Exercise objects.
     *
     * @param object the object to compare equality against.
     * @return true if this and object are the same; false if object is null or an object of different class;
     * true if all fields if this and other object are the same
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Exercise other = (Exercise) object;

        return this.name.equals(other.name) &&
                this.mode.equals(other.mode) &&
                Double.compare(this.weight, other.weight) == 0 &&
                this.sets == other.sets &&
                this.targetReps == other.targetReps &&
                this.setRepsList.equals(other.setRepsList); // Compare setRepsList
    }

    //Private helper methods

    /**
     * Validates the set index during rep updates.
     */
    private void validateSetIndex(int setIndex) {
        if (setIndex < 0 || setIndex >= sets) {
            throw new IllegalArgumentException("Set index " + setIndex + " is out of bounds for " + sets + " sets.");
        }
    }

    /**
     * Ensures reps are actually different before updating.
     */
    private void checkUpdateRepsDifferent(int currentReps, int newReps) {
        if (currentReps == newReps) {
            throw new IllegalArgumentException("New reps (" + newReps + ") must be different from current reps (" + currentReps + ").");
        }
    }

    /**
     * Verifies that the number of sets in an exercise is valid.
     */
    private void checkExerciseSetsIsValid(int sets) {
        if (sets < 1) {
            throw new IllegalArgumentException("Number of sets (" + sets + ") cannot be less than 1.");
        }
    }

    /**
     * Verifies the name of the exercise is valid.
     */
    private void checkExerciseNameValid(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be null or empty.");
        }
    }

    /**
     * Verifies that the weight is valid.
     */
    private void checkExerciseWeightValid(double weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight (" + weight + ") cannot be negative.");
        }
    }

    /**
     * Ensures that when updating mode, the current mode and new mode are different.
     * @param currentMode the current mode of the exercise.
     * @param newMode the new mode of the exercise.
     */
    private void checkUpdateModeDifferent(Mode currentMode, Mode newMode) {
        if (currentMode == newMode) {
            throw new IllegalArgumentException("Modes must be different for mode updating to work");
        }
    }

    /**
     * Validates that the setRepsList matches the number of sets.
     */
    private void validateSetRepsList(List<SetReps> setRepsList, int sets) {
        if (setRepsList.size() != sets) {
            throw new IllegalArgumentException("Number of SetReps objects must match the number of sets.");
        }
    }
}
