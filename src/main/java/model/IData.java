package model;

public interface IData {
    double delta(IExercise exercise);

    double mean(IExercise exercise);

    double mode(IExercise exercise);

    double standardDev(IExercise exercise);

    double range(IExercise exercise);

    double cumulativeSum(IExercise exercise);

    double zCore(IExercise exercise);
}
