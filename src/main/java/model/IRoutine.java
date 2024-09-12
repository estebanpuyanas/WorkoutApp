package model;

public interface IRoutine {
    IRoutine createRoutine(String name);

    void deleteRoutine();

    void editRoutine();

    void printRoutine();
}
