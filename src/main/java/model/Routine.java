package model;

import java.util.ArrayList;
import java.util.List;

public class Routine implements IRoutine {

    private String name;
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
    public void editRoutine(int oldIndex, int newIndex) {
        if(routine.size() < 2){
            System.out.println("A routine must have at least two exercises for it to be editable");
        }

        if(oldIndex == newIndex){
            System.out.println("Old index and new index are the same, no changes will be made");
            return;
        }

        if (oldIndex >= 0 && oldIndex < routine.size() && newIndex >= 0 && newIndex < routine.size()) {
            IWorkout workout = routine.remove(oldIndex); // Remove the workout at oldIndex
            routine.add(newIndex, workout); // Insert it at newIndex
        } else {
            System.out.println("Invalid indices for reordering workouts.");
        }
    }

    public String getName(){
        return name;
    }

    public int getRoutineSize(){
        return routine.size();
    }

    public void changeRoutineName(String newName){
        name = newName;
    }
    @Override
    public void printRoutine() {
        System.out.println(routine);
        for (IWorkout workout : routine) {
            System.out.println(workout.getExerciseList());
        }
    }
}
