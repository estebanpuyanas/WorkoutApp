package model;

public interface IRoutine {
    IRoutine createRoutine(String name);

    void deleteRoutine();

    void editRoutine(int oldIndex, int newIndex);

    void printRoutine();
}
