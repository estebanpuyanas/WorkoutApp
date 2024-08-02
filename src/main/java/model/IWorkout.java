package model;

public interface IWorkout {
    public void addWorkout(IRoutine routine);
    public void removeWorkout(IRoutine routine);
    public void editWorkout(IRoutine routine);
    public void printWorkout();
}
