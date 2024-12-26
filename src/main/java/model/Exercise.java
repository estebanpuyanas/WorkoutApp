package model;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
     * Data structure that stores the sets:reps as KV pair in map.
     */
    private final Map<Integer, Integer> repsPerSet;

    /**
     * the target amount of repetitions desired to be achived in a set.
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
     * @param repsPerSet the reps done per each set of the exercise.
     * @param targetReps the target amount of repetitions desired to be achived in a set.
     * @param weight     the weight of the exercise.
     * @param mode       the mode of the exercise.
     */
    public Exercise(String name, int sets, Map<Integer, Integer> repsPerSet, int targetReps, double weight, Mode mode) {
        this.name = name;
        this.sets = sets;
        this.repsPerSet = new HashMap<>(repsPerSet);
        this.targetReps = targetReps;
        this.weight = weight;
        this.mode = mode;

        checkExerciseNameValid(name);
        checkExerciseSetsIsValid(sets);
        checkExerciseWeightValid(weight);
    }

    /**
     * Allows for the creation of a new exercise.
     *
     * @param name       the given name.
     * @param sets       the number of sets the exercise will be done for.
     * @param repsPerSet the repetitions of the exercise per set. Set number is the Key, repetitions is Value.
     * @param targetReps the target amount of repetitions desired to be achived in a set.
     * @param weight     the weight being used for the exercise.
     * @param mode       the mode of the exercise.
     * @return a new exercise, with the specified parameters.
     */
    @Override
    public Exercise createExercise(String name, int sets, Map<Integer, Integer> repsPerSet, int targetReps, double weight, Mode mode) {
        return new Exercise(name, sets, repsPerSet, targetReps, weight, mode);
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
        return weight;
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
        return sets;
    }

    /**
     * Updates the reps at a specified set dynamically.
     *
     * @param setIndex the set that will be updated.
     * @param reps     the number of reps to assign to the set.
     */
    @Override
    public void updateReps(int setIndex, int reps) {
        validateSetIndex(setIndex);
        checkUpdateRepsDifferent(repsPerSet.getOrDefault(setIndex, -1), reps);
        repsPerSet.put(setIndex, reps);
    }

    /**
     * Obtains all the reps of every set.
     *
     * @return the reps of every set.
     */
    @Override
    public Map<Integer, Integer> getRepsForAllSets() {
        return Collections.unmodifiableMap(repsPerSet);
    }

    /**
     * Obtains the reps for the specified set.
     *
     * @param setIndex the specified set.
     * @return the number of reps.
     */
    @Override
    public Integer getRepsForSpecificSet(int setIndex) {
        validateSetIndex(setIndex);
        return repsPerSet.getOrDefault(setIndex, 0);
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
        return mode;
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
        return name;
    }

    /**
     * Gets the target reps of this exercise.
     * @return the given exercise's target reps.
     */
    @Override
    public int getTargetReps() {
        return targetReps;
    }

    /**
     * Updates the target repetitions of this exercise.
     * @param newTargetReps the new number of target reps.
     */
    @Override
    public void updateTargetReps(int newTargetReps) {
        if(newTargetReps == this.targetReps){
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
        // Ensure consistent formatting for the weight
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedWeight = df.format(this.getWeight());

        System.out.println(
                this.getName() + " " +
                        this.getSets() + "x" + this.getTargetReps() + "@" + formattedWeight +
                        " (Reps per set: " + this.getRepsForAllSets() + ")"
        );
    }

    /**
     * Overriden default hashcode method.
     * @return the hashcode of an objet.
     */
    @Override
    public int hashcode() {
        int result = name.hashCode();
        result = 31 * result + Integer.hashCode(sets);
        result = 31 * result + Integer.hashCode(targetReps);
        result = 31 * result + Double.hashCode(weight);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        return result;
    }

    /**
     * Overriden default equals method.
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
                this.targetReps == other.targetReps;
    }

    /**
     * Validates the set index during rep updates.
     */
    private void validateSetIndex(int setIndex) {
        if (setIndex < 0 || setIndex >= sets) {
            throw new IllegalArgumentException("Set index " + setIndex + " is out of bounds for " + sets + " sets.");
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
     * Ensures reps are actually different before updating.
     */
    private void checkUpdateRepsDifferent(int currentReps, int newReps) {
        if (currentReps == newReps) {
            throw new IllegalArgumentException("New reps (" + newReps + ") must be different from current reps (" + currentReps + ").");
        }
    }

    /**
     * Ensures that when updating mode, the current mode and new mode are different.
     * @param currentMode the currnet mode of the exercise.
     * @param newMode the new mode of the exercise.
     */
    private void checkUpdateModeDifferent(Mode currentMode, Mode newMode) {
        if (currentMode == newMode) {
            throw new IllegalArgumentException("Modes must be different for mode updating to work");
        }
    }
}
