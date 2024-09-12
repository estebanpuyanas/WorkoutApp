package model;

import java.util.ArrayList;
import java.util.List;

public class Routine implements IRoutine {

    private final String name;
    private final List<IWorkout> routine;

    public Routine(String name) {
        this.name = name;
        this.routine = new ArrayList<>();
    }

    @Override
    public IRoutine createRoutine(String name) {
        return new Routine(name);
    }

    @Override
    public void deleteRoutine() {
        this.routine.clear();
    }

    @Override
    public void editRoutine() {
        //TODO IMPLEMENT
    }

    @Override
    public void printRoutine() {
        System.out.println(routine);
        for (IWorkout workout : routine) {
            System.out.println(workout.getExerciseList());
        }
    }
}
