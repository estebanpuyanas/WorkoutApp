package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ExerciseTest {

    @Test
    public void createExerciseWorksOnValidInput() {
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise newExercise = new Exercise("Shoulder Press", 4, repsPerSet,8,50.00, Mode.DUMBBELL);


        Assert.assertEquals(newExercise.getName(), "Shoulder Press");
        Assert.assertEquals(newExercise.getSets(), 4);
        Assert.assertEquals(newExercise.getMode(), Mode.DUMBBELL);
        Assert.assertEquals(newExercise.getWeight(), 50.00, 0.00);
    }

    @Test
    public void getTargetRepsWorksOnValidInput(){

        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise exercise = new Exercise("Shoulder Press", 4, repsPerSet,8,50.00, Mode.DUMBBELL);

        Assert.assertEquals(8, exercise.getTargetReps());
    }

    @Test
    public void updateFunctionsOnExerciseWorkOnValidInput() {
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        repsPerSet.put(0, 5);
        Exercise newExercise = new Exercise("Shoulder Press", 4, repsPerSet,8,50.00, Mode.DUMBBELL);

        newExercise.updateName("Seated shoulder press");
        Assert.assertEquals("Seated shoulder press", newExercise.getName());

        newExercise.updateSets(1);
        Assert.assertEquals(1, newExercise.getSets());

        newExercise.updateMode(Mode.BARBELL);
        Assert.assertEquals(Mode.BARBELL, newExercise.getMode());

        newExercise.updateReps(0, 12);
        Assert.assertEquals(Integer.valueOf(12), newExercise.getRepsForSpecificSet(0));

        newExercise.updateTargetReps(12);
        Assert.assertEquals(12, newExercise.getTargetReps());

        newExercise.updateWeight(45.00);
        Assert.assertEquals(45.00, newExercise.getWeight(),0.00);
    }

    @Test
    public void updateFunctionsOnExerciseFailOnInvalidInput() {
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        repsPerSet.put(0, 5);
        Exercise newExercise = new Exercise("Shoulder Press", 4, repsPerSet,8,50.00, Mode.DUMBBELL);

        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateName(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateSets(-1));
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateReps(0, 5));
        Assert.assertThrows(NullPointerException.class, () -> newExercise.updateReps(-5, 5));
        Assert.assertThrows(NullPointerException.class, () -> newExercise.updateReps(3, 5));
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateMode(Mode.DUMBBELL));
    }

    @Test
    public void equalOverrideWorksWhenTrue(){
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise e1 = new Exercise("Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);
        Exercise e2 = new Exercise("Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);

        Assert.assertTrue(e1.equals(e2));
    }

    @Test
    public void equalOverrideFailsWhenFalse(){
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise e1 = new Exercise("Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);
        Exercise e2 = new Exercise("Incline Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);

        Assert.assertFalse((e1.equals(e2)));

    }
}
