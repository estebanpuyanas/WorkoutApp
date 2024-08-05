package model;

public interface IData {
    public double delta(IExercise exercise);
    public double mean(IExercise exercise);
    public double mode(IExercise exercise);
    public double standardDev(IExercise exercise);
    public double range(IExercise exercise);
    public double cumulativeSum(IExercise exercise);
    public double zCore(IExercise exercise);
}
