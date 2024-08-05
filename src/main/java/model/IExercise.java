package model;

public interface IExercise {
    public Exercise createExercise(String name, int sets, int reps, double weight, Mode mode);
    public void deleteExercise();
    public void updateWeight(double weight);
    public void updateSets(int sets);
    public int getSets();
    public void updateReps(int reps);
    public int getReps();
    public void setMode(Mode mode);
    public Mode getMode();
    public void setName(String name);
    public String getName();
    public double getWeight();

}
