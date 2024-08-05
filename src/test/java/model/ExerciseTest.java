package model;
import org.junit.Test;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class ExerciseTest {

    @Test
    public void createExerciseWorksOnValidInput(){
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise newExercise = new Exercise("Shoulder press",4, repsPerSet,50.00,Mode.DUMBBELL);


        Assert.assertEquals(newExercise.getName(), "Shoulder press");
        Assert.assertEquals(newExercise.getSets(), 4);
        Assert.assertEquals(newExercise.getMode(), Mode.DUMBBELL);
        //TODO FIND WAY TO TEST WEIGHT SINCE ASSERT IS DEPRECATED FOR DOUBLE.
    }

    @Test
    public void updateFunctionsOnExerciseWorkOnValidInput(){
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        repsPerSet.put(0,5);
        Exercise newExercise = new Exercise("Shoulder press",4, repsPerSet,50.00,Mode.DUMBBELL);

        newExercise.updateName("Seated shoulder press");
        Assert.assertEquals("Seated shoulder press", newExercise.getName());

        newExercise.updateSets(1);
        Assert.assertEquals(1, newExercise.getSets());

        newExercise.updateMode(Mode.BARBELL);
        Assert.assertEquals(Mode.BARBELL,newExercise.getMode());

        newExercise.updateReps(0,12);
        Assert.assertEquals(Integer.valueOf(12), newExercise.getRepsForSpecificSet(0));
    }

    @Test
    public void updateFunctionsOnExerciseFailOnInvalidInput(){
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        repsPerSet.put(0,5);
        Exercise newExercise = new Exercise("Shoulder press",4, repsPerSet,50.00,Mode.DUMBBELL);

        Assert.assertThrows(IllegalArgumentException.class, ()-> newExercise.updateName(""));
        Assert.assertThrows(IllegalArgumentException.class, ()-> newExercise.updateSets(-1));
        Assert.assertThrows(IllegalArgumentException.class, ()-> newExercise.updateReps(0,5));
        Assert.assertThrows(NullPointerException.class, ()-> newExercise.updateReps(-5,5));
        Assert.assertThrows(NullPointerException.class, ()-> newExercise.updateReps(3,5));
        Assert.assertThrows(IllegalArgumentException.class, ()-> newExercise.updateMode(Mode.DUMBBELL));
    }
}
