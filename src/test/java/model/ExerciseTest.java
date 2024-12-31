package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseTest {

    private Exercise testExercise;
    private List<SetReps> defaultRepsPerSet;

    @Before
    public void setup() {
        defaultRepsPerSet = new ArrayList<>();
        defaultRepsPerSet.add(new SetReps(1, 8));
        defaultRepsPerSet.add(new SetReps(2, 8));
        defaultRepsPerSet.add(new SetReps(3, 8));
        defaultRepsPerSet.add(new SetReps(4, 8));

        testExercise = new Exercise("Shoulder Press", 4, defaultRepsPerSet, 8, 50.00, Mode.DUMBBELL);
    }

    @Test
    public void ExerciseGettersWorksOnValidInput() {

        Assert.assertEquals(testExercise.getName(), "Shoulder Press");
        Assert.assertEquals(testExercise.getSets(), 4);
        Assert.assertEquals(testExercise.getMode(), Mode.DUMBBELL);
        Assert.assertEquals(testExercise.getWeight(), 50.00, 0.00);
        Assert.assertEquals(8, testExercise.getTargetReps());
    }

    @Test
    public void updateFunctionsOnExerciseWorkOnValidInput() {

        testExercise.updateName("Seated shoulder press");
        Assert.assertEquals("Seated shoulder press", testExercise.getName());

        testExercise.updateSets(1);
        Assert.assertEquals(1, testExercise.getSets());

        testExercise.updateMode(Mode.BARBELL);
        Assert.assertEquals(Mode.BARBELL, testExercise.getMode());

        testExercise.updateReps(0, 12);
        Assert.assertEquals(12, testExercise.getRepsForSpecificSet(0));

        testExercise.updateTargetReps(12);
        Assert.assertEquals(12, testExercise.getTargetReps());

        testExercise.updateWeight(45.00);
        Assert.assertEquals(45.00, testExercise.getWeight(),0.00);
    }

    @Test
    public void printExerciseOutputsCorrectFormat() {

        // Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        testExercise.printExercise();

        // Reset System.out
        System.setOut(System.out);

        String expectedOutput = "Shoulder Press 4x8@50.00 (Reps per set: {0=8, 1=8, 2=8, 3=8})\n";
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void printExerciseHandlesEmptyRepsPerSet() {

        List<SetReps> emptyRepsPerSet = new ArrayList<>();
        Exercise exerciseWithEmptyReps = new Exercise("Bicep Curl", 4, emptyRepsPerSet, 12, 25.00, Mode.BARBELL);

        // Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        exerciseWithEmptyReps.printExercise();


        System.setOut(System.out);

        String expectedOutput = "Bicep Curl 4x12@25.00 (Reps per set: [])\n";
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void updateFunctionsOnExerciseFailOnInvalidInput() {

        Assert.assertThrows(IllegalArgumentException.class, () -> testExercise.updateName(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> testExercise.updateSets(-1));
        Assert.assertThrows(IllegalArgumentException.class, () -> testExercise.updateReps(0, 5));
        Assert.assertThrows(IllegalArgumentException.class, () -> testExercise.updateReps(-5, 5)); //negative index fails
        Assert.assertThrows(IllegalArgumentException.class, () -> testExercise.updateReps(5, 5)); //index > sets fails
        Assert.assertThrows(IllegalArgumentException.class, () -> testExercise.updateTargetReps(8));
        Assert.assertThrows(IllegalArgumentException.class, () -> testExercise.updateMode(Mode.DUMBBELL));
        Assert.assertThrows(IllegalArgumentException.class, () -> testExercise.updateWeight(-65.00));
    }

    @Test
    public void equalOverrideWorksWhenTrue() {

        // Create a new exercise that is the same as in @before setup method.
        IExercise testExercise2;

        defaultRepsPerSet = new ArrayList<>();
        defaultRepsPerSet.add(new SetReps(1, 8));
        defaultRepsPerSet.add(new SetReps(2, 8));
        defaultRepsPerSet.add(new SetReps(3, 8));
        defaultRepsPerSet.add(new SetReps(4, 8));

        testExercise2 = new Exercise("Shoulder Press", 4, defaultRepsPerSet, 8, 50.00, Mode.DUMBBELL);

        Assert.assertTrue(testExercise.equals(testExercise2));
        Assert.assertEquals(testExercise.hashcode(), testExercise2.hashcode());
    }

    @Test
    public void equalOverrideWorksWithSameObject() {
        Assert.assertTrue(testExercise.equals(testExercise));
    }

    @Test
    public void equalOverrideFailsWhenFalse(){

        IExercise testExercise2;

        defaultRepsPerSet = new ArrayList<>();
        defaultRepsPerSet.add(new SetReps(1, 8));
        defaultRepsPerSet.add(new SetReps(2, 8));
        defaultRepsPerSet.add(new SetReps(3, 8));
        defaultRepsPerSet.add(new SetReps(4, 8));

        testExercise2 = new Exercise("Bench Press", 4, defaultRepsPerSet, 12, 65.00, Mode.DUMBBELL);

        Assert.assertFalse((testExercise.equals(testExercise2)));
        Assert.assertNotEquals(testExercise.hashcode(), testExercise2.hashcode());
    }

    @Test
    public void equalOverrideReturnsFalseWhenNull(){
        Exercise e2 = null;

        Assert.assertFalse((testExercise.equals(e2)));
    }

    @Test
    public void sameHashCodeForSameObject() {

        int hashcode1 = testExercise.hashcode();
        int hashcode2 = testExercise.hashcode();

        Assert.assertEquals(hashcode1, hashcode2);
    }

    @Test
    public void consistentHashCodeForEqualObjects() {

        IExercise testExercise2;

        defaultRepsPerSet = new ArrayList<>();
        defaultRepsPerSet.add(new SetReps(1, 8));
        defaultRepsPerSet.add(new SetReps(2, 8));
        defaultRepsPerSet.add(new SetReps(3, 8));
        defaultRepsPerSet.add(new SetReps(4, 8));

        testExercise2 = new Exercise("Shoulder Press", 4, defaultRepsPerSet, 8, 50.00, Mode.DUMBBELL);

        Assert.assertEquals(testExercise.hashcode(), testExercise2.hashcode());
    }

    @Test
    public void differentHashCodeForUnequalObjects() {
        IExercise testExercise2;

        defaultRepsPerSet = new ArrayList<>();
        defaultRepsPerSet.add(new SetReps(1, 8));
        defaultRepsPerSet.add(new SetReps(2, 8));
        defaultRepsPerSet.add(new SetReps(3, 8));
        defaultRepsPerSet.add(new SetReps(4, 8));

        testExercise2 = new Exercise("Shoulder Press", 4, defaultRepsPerSet, 8, 50.00, Mode.DUMBBELL);


        Assert.assertNotEquals(testExercise, testExercise2);
    }

    // Checks whether hashcode implementation allows for object to work as key of hashmap.
    @Test
    public void hashCodeWorksInHashMap() {
        HashMap<Exercise, String> map = new HashMap<>();
        map.put(testExercise, "Valid Exercise");

        Assert.assertTrue(map.containsKey(testExercise));
        Assert.assertEquals("Valid Exercise", map.get(testExercise));
    }

}
