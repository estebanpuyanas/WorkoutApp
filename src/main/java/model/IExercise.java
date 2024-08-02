package model;

public interface IExercise {
    String getExerciseID();
    void setExerciseID(String exerciseID);
    String getWorkoutID();
    void setWorkoutID(String workoutID);
    String getName();
    void setName(String name);
    int setSets();
    void getSets(int sets);
    int setReps();
    void getReps(int reps);
    double getWeight();
    void setWeight(double weight);
}
