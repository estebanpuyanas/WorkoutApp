package model;

import java.util.ArrayList;
import java.util.List;

public class Workout implements IWorkout {

    private String name;
    private List<IExercise> exerciseList;

    public Workout(String name) {
        this.name = name;
        this.exerciseList = new ArrayList<>();
    }

    public Workout createWorkout(String name){
        return new Workout(name);
    }

    @Override
    public void addExercise(IExercise exercise) {
        this.exerciseList.add(exercise);
    }

    @Override
    public void removeExercise(IExercise exercise) {
        this.exerciseList.remove(exercise);
    }

    @Override
    public void editExercise(IExercise previousExercise, IExercise newExercise) {
        int index = this.exerciseList.indexOf(previousExercise);
        if(index != -1){
            this.exerciseList.set(index, newExercise);
        }
    }

    @Override
    public List<IExercise> getExerciseList(){
        return exerciseList;
    }

    @Override
    public void printWorkout() {
        System.out.println("Workout: " + this.name);
        for (IExercise exercise : exerciseList) {
            System.out.println(exercise.getName() + "\n" +
                    exercise.getSets() + " sets" + exercise.getReps() + " reps");
        }
    }
}
