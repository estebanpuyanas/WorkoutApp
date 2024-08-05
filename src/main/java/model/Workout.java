package model;

import java.util.ArrayList;
import java.util.List;

public class Workout implements IWorkout {

    private String name;
    private List<IExercise> exerciseList;

    public Workout(String name) {
        this.name = name;
        this.exerciseList = new ArrayList<>();
        checkWorkoutSizeValid(exerciseList);
    }

    public Workout createWorkout(String name){
        checkNameWorkoutNameValid(name);
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
    public void deleteExercise(IExercise exercise){
        exercise.deleteExercise();
        this.exerciseList.removeIf(IExercise::isDeleted);
    }

    @Override
    public void editExercise(IExercise previousExercise, IExercise newExercise) {
        checkExerciseEditIsDifferent(previousExercise, newExercise);
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
                    exercise.getSets() + " sets" + exercise.getRepsForAllSets() + " reps");
        }
    }

    private void checkNameWorkoutNameValid(String name){
        if(name.isEmpty()){
            throw new IllegalArgumentException("Workout must have a name");
        }
    }

    private void checkWorkoutSizeValid(List<IExercise> exerciseList){
        if(exerciseList.isEmpty()){
            throw new IllegalStateException("A workout must have at least one exercise");
        }
    }

    private void checkExerciseEditIsDifferent(IExercise previousExercise, IExercise newExercise){
        if(previousExercise.equals(newExercise)){
            throw new IllegalStateException("An exercise must be different in at least one capacity for edit");
        }
    }
}
