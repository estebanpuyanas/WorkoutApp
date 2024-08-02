package model;

public interface IExercise {
    public void createExercise(String name, int sets, int reps);
    public void deleteExercise();
    public void updateWeight(double weight);
    public int updateSets(int sets);
    public int updateReps(int reps);
}
