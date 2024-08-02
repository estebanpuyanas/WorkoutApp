package model;

/**
 * This interface represents a workout routine. It allows for creation, deletion,
 * and, modificaiton of routine. Additionally, a routine can be printed.
 */
public interface IRoutine {

    public void createRoutine();
    public void deleteRoutine();
    public void editRoutine();
    public void printRoutine();
}
