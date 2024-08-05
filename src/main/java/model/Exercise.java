package model;

public class Exercise implements IExercise {
    private String name;
    private int sets;
    private int reps;
    private double weight;
    private Mode mode;

    public Exercise(String name, int sets, int reps, double weight, Mode mode) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.mode = mode;
    }

    @Override
    public Exercise createExercise(String name, int sets, int reps, double weight, Mode mode) {
        return new Exercise(name, sets, reps, weight, mode);
    }

    @Override
    public void deleteExercise() {
    //TODO: IMPLEMENT OR MAYBE MOVE TO OTEHR SECTION
    }

    @Override
    public void updateWeight(double weight) {
        this.weight = weight;
    }
    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void updateSets(int sets) {
        this.sets = sets;
    }

    @Override
    public int getSets(){
        return this.sets;
    }

    @Override
    public void updateReps(int reps) {
        this.reps = reps;
    }

    @Override
    public int getReps(){
        return this.reps;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public Mode getMode() {
        return this.mode;
    }

    @Override
   public  String getName(){
        return name;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }
}
