package model;

import java.util.Map;

/**
 * Implementation of IExercise interface that represents an exercise. An exercise is composed of the following parameters:
 * 1. Name
 * 2. Number of sets
 * 3. Repetitions per set.
 * 4. Weight used in exercise.
 * 5. Mod
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
     * Flag checking whether exercise has been deleted.
     */
    private boolean isDeleted;


    /**
     * Default constructor for a new exercise.
     *
     * @param name       the name of the exercise.
     * @param sets       the sets of the exercise.
     * @param repsPerSet the reps done per each set of the exercise.
     * @param weight     the weight of the exercise.
     * @param mode       the mode of the exercise.
     */
    public Exercise(String name, int sets, Map<Integer, Integer> repsPerSet, int targetReps, double weight, Mode mode) {
        this.name = name;
        this.sets = sets;
        this.repsPerSet = repsPerSet;
        this.targetReps = targetReps;
        this.weight = weight;
        this.mode = mode;

        checkExerciseNameValid(name);
        checkExerciseSetsIsValid(sets);
        checkExerciseRepsValid(repsPerSet);
        checkExerciseWeightValid(weight);
    }

    /**
     * Allows for the creation of a new exercise.
     *
     * @param name       the given name.
     * @param sets       the number of sets the exercise will be done for.
     * @param repsPerSet the repetitions of the exercise per set. Set number is the Key, repetitions is Value.
     * @param weight     the  weight being used for the exercise.
     * @param mode       the mode of the exercise.
     * @return a new exercise, with the specified parameters.
     */
    @Override
    public Exercise createExercise(String name, int sets, Map<Integer, Integer> repsPerSet,int targetReps, double weight, Mode mode) {
        return new Exercise(name, sets, repsPerSet, targetReps, weight, mode);
    }

    /**
     * Deletes an exercise.
     */
    @Override
    public void deleteExercise() {
        this.isDeleted = true;
    }

    /**
     * Assesses whether an exercise has been deleted.
     *
     * @return whether the exercise has been deleted.
     */
    @Override
    public boolean isDeleted() {
        return isDeleted;
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
     * Updates the reps at a specified set.
     *
     * @param setIndex the set that will be updated.
     * @param reps     the number of reps to assign to the set.
     */
    @Override
    public void updateReps(int setIndex, int reps) {
        checkUpdateRepsDifferent(getRepsForSpecificSet(setIndex), reps);
        if (setIndex >= 0 && setIndex < sets) {
            this.repsPerSet.put(setIndex, reps);
        } else {
            throw new IllegalArgumentException("Invalid set index");
        }
    }

    /**
     * Obtains all the reps of every set.
     *
     * @return the reps of every set.
     */
    @Override
    public Map<Integer, Integer> getRepsForAllSets() {
        return repsPerSet;
    }

    /**
     * Obtains the reps for the specified set.
     *
     * @param setIndex the specified set.
     * @return the nuumber of reps.
     */
    @Override
    public Integer getRepsForSpecificSet(int setIndex) {
        return this.repsPerSet.get(setIndex);
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
     * Gets the name of this exercise.
     *
     * @return the name of the exercise.
     */
    @Override
    public String getName() {
        return name;
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

    @Override
    public int getTargetReps(){
        return targetReps;
    }

    @Override
    public void updateTargetReps(int newTargetReps){
        this.targetReps = newTargetReps;
    }

    @Override
    public int hashcode(){
        int result = name.hashCode();
        result = 31 * result + Integer.hashCode(sets);
        result = 31 * result + Integer.hashCode(targetReps);
        result = 31 * result + Double.hashCode(weight);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object object){

        if(this == object){
            return true;
        }

        if(object == null || getClass() != object.getClass()){
            return  false;
        }

        Exercise other = (Exercise) object;

        return this.name.equals(other.name) &&
                this.mode.equals(other.mode) &&
                Double.compare(this.weight, other.weight) == 0 &&
                this.sets == other.sets &&
                this.targetReps == other.targetReps;
    }

    @Override
    public void printExercise(){
        System.out.println(this.getName() + " " + this.getSets() + "x" + this.getTargetReps() + "@" + this.getWeight());
    }

    /**
     * Verifies that the assigned name for an exercise is valid (cannot be empty).
     *
     * @param name the name of the exercise.
     * @throws IllegalArgumentException if the name is invalid
     */
    private void checkExerciseNameValid(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("An exercise must have a name");
        }
    }

    /**
     * Verifies that the assigned weight to an exercise is valid (cannot be negative).
     *
     * @param weight the weight of the exercise.
     * @throws IllegalArgumentException if the  parameter is invalid.
     */
    private void checkExerciseWeightValid(double weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
    }

    /**
     * Verifies that the reps on each set are valid (cannot be less than 0).
     *
     * @param repsPerSet the map where the reps per set are stored as a KV pair.
     * @throws IllegalArgumentException if the value (reps) is less than 0.
     */
    private void checkExerciseRepsValid(Map<Integer, Integer> repsPerSet) {
        for (Map.Entry<Integer, Integer> entries : repsPerSet.entrySet()) {
            if (entries.getValue() < 0) {
                throw new IllegalArgumentException("Reps cannot be less than 0 for any given set");
            }
        }
    }

    /**
     * Verifies that the number of sets un an exercise is valid (not less than 1).
     *
     * @param sets the number of sets in the exercise.
     * @throws IllegalArgumentException if the paramter is invalid.
     */
    private void checkExerciseSetsIsValid(int sets) {
        if (sets < 1) {
            throw new IllegalArgumentException("Number of sets cannot be less than 1");
        }
    }

    private void checkUpdateRepsDifferent(int currentReps, int newReps) {
        if (currentReps == newReps) {
            throw new IllegalArgumentException("Rep number must be different in order to update");
        }
    }

    private void checkUpdateModeDifferent(Mode currentMode, Mode newMode) {
        if (currentMode == newMode) {
            throw new IllegalArgumentException("Modes must be different for mode updating to work");
        }
    }
}
