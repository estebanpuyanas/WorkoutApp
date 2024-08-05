package model;

import java.util.Map;

/**
 * Interface that represents an exercise. An exercise is composed of the following parameters:
 * 1. Name
 * 2. Number of sets
 * 3. Repetitions per set.
 * 4. Weight used in exercise.
 * 5. Mode (machine, free weights (dumbbell/barbell), cables.
 */
public interface IExercise {

    /**
     * Allows for the creation of a new exercise.
     * @param name the given name.
     * @param sets the number of sets the exercise will be done for.
     * @param repsPerSet the repetitions of the exercise per set. Set number is the Key, repetitions is Value.
     * @param weight the  weight being used for the exercise.
     * @param mode the mode of the exercise.
     * @return a new exercise, with the specified parameters.
     */
    public Exercise createExercise(String name, int sets, Map<Integer, Integer> repsPerSet, double weight, Mode mode);

    /**
     * Deletes an exercise.
     */
    public void deleteExercise();

    /**
     * Assesses whether an exercise has been deleted.
     * @return whether the exercise has been deleted.
     */
    public boolean isDeleted();

    /**
     * Updates the weight being used on this exercise.
     * @param weight the new weight.
     */
    public void updateWeight(double weight);

    /**
     * Obtains the current weight of this exercise.
     * @return the given weight.
     */
    public double getWeight();

    /**
     * Updates the amount of sets for this exercise.
     * @param sets the new amount of sets.
     */
    public void updateSets(int sets);

    /**
     * Gets the current number of sets for this exercise.
     * @return the number of sets.
     */
    public int getSets();

    /**
     * Updates the reps at a specified set.
     * @param setIndex the set that will be updated.
     * @param reps the number of reps to assign to the set.
     */
    public void updateReps(int setIndex, int reps);

    /**
     * Obtains all the reps of every set.
     * @return the reps of every set.
     */
    public Map<Integer,Integer> getRepsForAllSets();

    /**
     * Obtains the reps for the specified set.
     * @param setIndex the specified set.
     * @return the nuumber of reps.
     */
    public Integer getRepsForSpecificSet(int setIndex);

    /**
     * Sets the mode of this exercise.
     * @param mode the mode.
     */
    public void updateMode(Mode mode);

    /**
     * Obtains the mode of this exercise.
     * @return the exercise mode.
     */
    public Mode getMode();

    /**
     * Updates the name of this exercise.
     * @param name the new name.
     */
    public void updateName(String name);

    /**
     * Gets the name of this exercise.
     * @return the name of the exercise.
     */
    public String getName();
}
