package model;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class ExerciseTest {

    @Test
    public void ExerciseGettersWorksOnValidInput() {
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise newExercise = new Exercise("Shoulder Press", 4, repsPerSet,8,50.00, Mode.DUMBBELL);

        Assert.assertEquals(newExercise.getName(), "Shoulder Press");
        Assert.assertEquals(newExercise.getSets(), 4);
        Assert.assertEquals(newExercise.getMode(), Mode.DUMBBELL);
        Assert.assertEquals(newExercise.getWeight(), 50.00, 0.00);
        Assert.assertEquals(8, newExercise.getTargetReps());
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
    public void printExerciseOutputsCorrectFormat() {

        Map<Integer, Integer> repsPerSet = new HashMap<>();
        repsPerSet.put(0, 8);
        repsPerSet.put(1, 8);
        repsPerSet.put(2, 8);
        repsPerSet.put(3, 8);

        Exercise exercise = new Exercise("Shoulder Press", 4, repsPerSet, 8, 50.00, Mode.DUMBBELL);

        // Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        exercise.printExercise();

        // Reset System.out
        System.setOut(System.out);

        String expectedOutput = "Shoulder Press 4x8@50.00 (Reps per set: {0=8, 1=8, 2=8, 3=8})\n";
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void printExerciseHandlesEmptyRepsPerSet() {

        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise exercise = new Exercise("Bicep Curl", 4, repsPerSet, 12, 25.00, Mode.BARBELL);

        // Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        exercise.printExercise();

        // Reset System.out
        System.setOut(System.out);

        String expectedOutput = "Bicep Curl 4x12@25.00 (Reps per set: {})\n";
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void updateFunctionsOnExerciseFailOnInvalidInput() {
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        repsPerSet.put(0, 5);
        Exercise newExercise = new Exercise("Shoulder Press", 4, repsPerSet,8,50.00, Mode.DUMBBELL);

        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateName(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateSets(-1));
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateReps(0, 5));
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateReps(-5, 5)); //negative index fails
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateReps(5, 5)); //index > sets fails
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateTargetReps(8));
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateMode(Mode.DUMBBELL));
        Assert.assertThrows(IllegalArgumentException.class, () -> newExercise.updateWeight(-65.00));
    }

    @Test
    public void equalOverrideWorksWhenTrue(){
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise e1 = new Exercise("Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);
        Exercise e2 = new Exercise("Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);

        Assert.assertTrue(e1.equals(e2));
    }

    @Test
    public void equalOverrideWorksWithSameObject() {

        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise e1 = new Exercise("Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);
        Assert.assertTrue(e1.equals(e1));
    }

    @Test
    public void equalOverrideFailsWhenFalse(){
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise e1 = new Exercise("Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);
        Exercise e2 = new Exercise("Incline Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);

        Assert.assertFalse((e1.equals(e2)));
    }

    @Test
    public void equalOverrideReturnsFalseWhenNull(){
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        Exercise e1 = new Exercise("Bench Press", 4, repsPerSet, 8, 65.00, Mode.DUMBBELL);
        Exercise e2 = null;

        Assert.assertFalse((e1.equals(e2)));
    }

    @Test
    public void sameHashCodeForSameObject() {
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        repsPerSet.put(0, 8);
        repsPerSet.put(1, 8);
        Exercise exercise = new Exercise("Shoulder Press", 4, repsPerSet, 8, 50.0, Mode.DUMBBELL);

        int hashcode1 = exercise.hashcode();
        int hashcode2 = exercise.hashcode();

        Assert.assertEquals(hashcode1, hashcode2);
    }

    @Test
    public void consistentHashCodeForEqualObjects() {

        Map<Integer, Integer> repsPerSet1 = new HashMap<>();
        repsPerSet1.put(0, 8);
        repsPerSet1.put(1, 8);
        Exercise exercise1 = new Exercise("Shoulder Press", 4, repsPerSet1, 8, 50.0, Mode.DUMBBELL);

        Map<Integer, Integer> repsPerSet2 = new HashMap<>();
        repsPerSet2.put(0, 8);
        repsPerSet2.put(1, 8);
        Exercise exercise2 = new Exercise("Shoulder Press", 4, repsPerSet2, 8, 50.0, Mode.DUMBBELL);

        Assert.assertEquals(exercise1.hashcode(), exercise2.hashcode());
    }

    @Test
    public void differentHashCodeForUnequalObjects() {

        Map<Integer, Integer> repsPerSet1 = new HashMap<>();
        repsPerSet1.put(0, 8);
        repsPerSet1.put(1, 8);
        Exercise exercise1 = new Exercise("Bench Press", 4, repsPerSet1, 8, 65.00, Mode.BARBELL);

        Map<Integer, Integer> repsPerSet2 = new HashMap<>();
        repsPerSet2.put(0, 8);
        repsPerSet2.put(1, 8);
        Exercise exercise2 = new Exercise("Shoulder Press", 4, repsPerSet2, 8, 50.0, Mode.DUMBBELL);

        Assert.assertNotEquals(exercise1, exercise2);
    }

    // Checks whether hashcode implementation allows for object to work as key of hashmap.
    @Test
    public void hashCodeWorksInHashMap() {

        Map<Integer, Integer> repsPerSet = new HashMap<>();
        repsPerSet.put(0, 8);
        repsPerSet.put(1, 8);
        Exercise exercise = new Exercise("Shoulder Press", 4, repsPerSet, 8, 50.0, Mode.DUMBBELL);

        HashMap<Exercise, String> map = new HashMap<>();
        map.put(exercise, "Valid Exercise");

        Assert.assertTrue(map.containsKey(exercise));
        Assert.assertEquals("Valid Exercise", map.get(exercise));
    }
}
