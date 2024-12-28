package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutTest {

    // Private workout field created to be accessible by all methods (tests in the class).
    private Workout testWorkout;

    // Create factory setup method to reduce code duplication.
    @Before
    public void setup() {

        // Create a workout
        testWorkout = new Workout("Test Workout");

        // Create and add exercises
        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        testWorkout.addExercise(new Exercise("Bench Press", 3, benchReps, 10, 65.00, Mode.DUMBBELL));

        Map<Integer, Integer> squatReps = new HashMap<>();
        squatReps.put(0, 12);
        squatReps.put(1, 10);
        squatReps.put(2, 10);
        testWorkout.addExercise(new Exercise("Squat", 3, squatReps, 12, 185.00, Mode.BARBELL));

        Map<Integer, Integer> pullupReps = new HashMap<>();
        pullupReps.put(0, 15);
        pullupReps.put(1, 12);
        pullupReps.put(2, 12);
        testWorkout.addExercise(new Exercise("Pull-ups", 3, pullupReps, 15, 0.00, Mode.BODYWEIGHT));
    }

    @Test
    public void workoutWorksWithAtLeastOneExercise() {
        Assert.assertFalse("The workout should have at least one exercise.", testWorkout.getExerciseList().isEmpty());
    }

    @Test
    public void workoutThrowsExceptionWhenLessThanOneExercise() {
        Workout emptyWorkout = new Workout("Empty Workout Test");
        Map<Integer, Integer> repsPerSet = new HashMap<>();


        Exercise e1 = new Exercise("Bench press",4, repsPerSet, 8, 65.00, Mode.DUMBBELL);
        Exercise e2 = new Exercise("Shoulder press",4, repsPerSet, 8, 50.00, Mode.DUMBBELL);

        emptyWorkout.addExercise(e1);
        emptyWorkout.addExercise(e2);

        emptyWorkout.removeExercise(e1);
        Assert.assertThrows(IllegalStateException.class, () -> emptyWorkout.removeExercise(e2));
    }

    @Test
    public void addExerciseToWorkoutFunctionsOnValidInput() {

        Map<Integer, Integer> shoulderPressReps = new HashMap<>();
        shoulderPressReps.put(0, 10);
        shoulderPressReps.put(1, 10);
        shoulderPressReps.put(2, 8);
        IExercise shoulderPress = new Exercise("Shoulder Press", 3, shoulderPressReps, 10, 50.0, Mode.DUMBBELL);
        testWorkout.addExercise(shoulderPress);

        Assert.assertEquals(4, testWorkout.getExerciseList().size());
    }

    @Test
    public void addExerciseToWorkoutRestoresDeletedExercise() {

        // Arrange
        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        IExercise benchPress = new Exercise("Bench Press", 3, benchReps, 10, 200.0, Mode.BARBELL);

        testWorkout.addExercise(benchPress);  // Add to currentExercises
        testWorkout.removeExercise(benchPress);  // Move to deletedExercises

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));


        testWorkout.addExercise(benchPress);  // Restore to currentExercises

        // Reset System.out
        System.setOut(System.out);

        Assert.assertTrue(testWorkout.getExerciseList().contains(benchPress));  // In currentExercises
        Assert.assertFalse(testWorkout.getDeletedExercises().contains(benchPress));  // Not in deletedExercises

        String expectedOutput = "Exercise \"Bench Press\" restored to workout \"Test Workout\".\n";
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void addExerciseToWorkoutRejectsNullInput() {
        Assert.assertThrows(IllegalArgumentException.class, () -> testWorkout.addExercise(null));
    }

    @Test
    public void addExerciseToWorkoutRejectsDuplicates() {

        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);

        Assert.assertThrows(IllegalStateException.class,
                () -> testWorkout.addExercise(new Exercise("Bench Press", 3, benchReps, 10, 65.00, Mode.DUMBBELL)));
    }

    @Test
    public void removeExerciseFromWorkoutWorksOnValidInput() {

        IExercise benchPress = testWorkout.getExerciseList().get(0);
        testWorkout.removeExercise(benchPress);

        Assert.assertEquals(2, testWorkout.getExerciseList().size());
        Assert.assertFalse(testWorkout.getExerciseList().contains(benchPress));
    }

    @Test
    public void removeExerciseFromWorkoutRejectsInexistentExercise() {

        // Create exercise, but do not add it to the workout.
        // This will test whether the method detects that it cannot delete exercises that do not exist.
        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        IExercise benchPress = new Exercise("Bench Press", 3, benchReps, 10, 200.0, Mode.BARBELL);

        IllegalArgumentException exception = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> testWorkout.removeExercise(benchPress)); // Attempt to remove a non-existent exercise
    }

    @Test
    public void editExerciseWorksOnValidInput() {

        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        IExercise benchPress = new Exercise("Bench Press", 3, benchReps, 10, 200.0, Mode.BARBELL);

        Map<Integer, Integer> updatedBenchReps = new HashMap<>();
        updatedBenchReps.put(0, 12);
        updatedBenchReps.put(1, 12);
        updatedBenchReps.put(2, 10);
        IExercise updatedBenchPress = new Exercise("Bench Press", 3, updatedBenchReps, 12, 210.0, Mode.BARBELL);

        testWorkout.addExercise(benchPress);

        testWorkout.editExercise(benchPress, updatedBenchPress);

        Assert.assertTrue(testWorkout.getExerciseList().contains(updatedBenchPress));
        Assert.assertFalse(testWorkout.getExerciseList().contains(benchPress));
    }

    @Test
    public void editExerciseFailsWhenCurrentExerciseIsNull() {

        Map<Integer, Integer> updatedBenchReps = new HashMap<>();
        updatedBenchReps.put(0, 12);
        updatedBenchReps.put(1, 12);
        updatedBenchReps.put(2, 10);
        IExercise updatedBenchPress = new Exercise("Bench Press", 3, updatedBenchReps, 12, 210.0, Mode.BARBELL);


        Assert.assertThrows(IllegalArgumentException.class, () -> testWorkout.editExercise(null, updatedBenchPress));
    }

    @Test
    public void editExerciseFailsWhenNewExerciseIsNull() {

        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        IExercise benchPress = new Exercise("Bench Press", 3, benchReps, 10, 200.0, Mode.BARBELL);

        testWorkout.addExercise(benchPress);

        Assert.assertThrows(IllegalArgumentException.class, () -> testWorkout.editExercise(benchPress, null));
    }

    @Test
    public void editExerciseFailsWhenExercisesAreIdentical() {

        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        IExercise benchPress = new Exercise("Bench Press", 3, benchReps, 10, 200.0, Mode.BARBELL);

        testWorkout.addExercise(benchPress);

        Assert.assertThrows(IllegalStateException.class, () -> testWorkout.editExercise(benchPress, benchPress));
    }

    @Test
    public void editExerciseFailsWhenCurrentExerciseNotFound() {

        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        IExercise benchPress = new Exercise("Bench Press", 3, benchReps, 10, 200.0, Mode.BARBELL);

        Map<Integer, Integer> updatedBenchReps = new HashMap<>();
        updatedBenchReps.put(0, 12);
        updatedBenchReps.put(1, 12);
        updatedBenchReps.put(2, 10);
        IExercise updatedBenchPress = new Exercise("Bench Press", 3, updatedBenchReps, 12, 210.0, Mode.BARBELL);

        Assert.assertThrows(IllegalArgumentException.class, () -> testWorkout.editExercise(benchPress, updatedBenchPress));
    }

    @Test
    public void restoreExerciseWorksOnValidInput() {
        // Setup
        Workout workout = new Workout("Test Workout");
        Map<Integer, Integer> repsPerSet = new HashMap<>();
        IExercise exercise1 = new Exercise("Bench Press", 4, repsPerSet, 12, 65.00, Mode.DUMBBELL);
        IExercise exercise2 = new Exercise("Shoulder Press", 4, repsPerSet, 12, 50.00, Mode.DUMBBELL);
        workout.addExercise(exercise2);

        // Add exercise to currentExercises list
        workout.addExercise(exercise1);

        // Remove exercise (moves it to deletedExercises list)
        workout.removeExercise(exercise1);

        // Call restoreExercise (moves it back to currentExercises list)
        workout.restoreExercise(exercise1);

        // Assertions
        Assert.assertTrue("Exercise should be in currentExercises after restoration.",
                workout.getCurrentExercises().contains(exercise1));
        Assert.assertFalse("Exercise should be removed from deletedExercises after restoration.",
                workout.getDeletedExercises().contains(exercise1));
    }

    @Test
    public void getNameWorks() {
        String expected = "Test Workout";
        Assert.assertEquals(expected, testWorkout.getWorkoutName());
    }

    @Test
    public void setWorkOutNameWorksOnValidInput() {

        String newWorkoutName = "New Test Workout Name";
        testWorkout.setWorkoutName(newWorkoutName);
        Assert.assertEquals(newWorkoutName, testWorkout.getWorkoutName());
    }

    @Test
    public void setWorkoutNameRejectsEmptyStirngInput() {

        String newName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> testWorkout.setWorkoutName(newName));
    }

    @Test
    public void setWorkoutNameRejectsNullInput() {

        String newName = null;
        Assert.assertThrows(IllegalArgumentException.class, () -> testWorkout.setWorkoutName(newName));
    }

    @Test
    public void printWorkoutPrintsCorrectOutput() {
        // Capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        testWorkout.printWorkout();

        // Reset System.out to its original state
        System.setOut(System.out);

        // Assert
        String expectedOutput = """
        Test Workout:
        1. Bench Press 3x10@65.00 (Reps per set: {0=10, 1=10, 2=8})
        2. Squat 3x12@185.00 (Reps per set: {0=12, 1=10, 2=10})
        3. Pull-ups 3x15@0.00 (Reps per set: {0=15, 1=12, 2=12})
        """;

        Assert.assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    public void printWorkoutHandlesEmptyWorkout() {

        Workout workout = new Workout("Empty Workout");

        // Capture the output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Call printWorkout
            workout.printWorkout();

            // Assert output
            String expectedOutput = "\nEmpty Workout:\nNo exercises in this workout.\n";
            Assert.assertEquals("The output for an empty workout is not as expected.", expectedOutput, outputStream.toString());
            // The finally keyword is used in try-catch block and guarantees a section of code will be executed even if exception is thrown
            // This is useful for this test because since we want to capture the thrown exception it is still necessary to reset the printStream.
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    public void equalsWorksOnIdenticalWorkOuts() {

        // Create same workout as in @before setup method.
        Workout identicalWorkout = new Workout("Test Workout");

        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        identicalWorkout.addExercise(new Exercise("Bench Press", 3, benchReps, 10, 65.00, Mode.DUMBBELL));

        Map<Integer, Integer> squatReps = new HashMap<>();
        squatReps.put(0, 12);
        squatReps.put(1, 10);
        squatReps.put(2, 10);
        identicalWorkout.addExercise(new Exercise("Squat", 3, squatReps, 12, 185.00, Mode.BARBELL));

        Map<Integer, Integer> pullupReps = new HashMap<>();
        pullupReps.put(0, 15);
        pullupReps.put(1, 12);
        pullupReps.put(2, 12);
        identicalWorkout.addExercise(new Exercise("Pull-ups", 3, pullupReps, 15, 0.00, Mode.BODYWEIGHT));

        debugWorkoutEquality(testWorkout, identicalWorkout);

        Assert.assertEquals(testWorkout, identicalWorkout);
        Assert.assertEquals(testWorkout.hashcode(), identicalWorkout.hashcode());
    }

    private void debugWorkoutEquality(Workout w1, Workout w2) {
        System.out.println("Comparing workouts...");
        System.out.println("Workout Names: " + w1.getWorkoutName() + " vs. " + w2.getWorkoutName());

        System.out.println("Workout Exercises:");
        List<IExercise> w1Exercises = w1.getExerciseList();
        List<IExercise> w2Exercises = w2.getExerciseList();

        System.out.println("Test Workout Exercises:");
        for (IExercise ex : w1Exercises) {
            System.out.println("  " + ex.getName() + ", hashCode: " + ex.hashCode() + ", repsPerSet: " + ex.getRepsForAllSets().hashCode());
        }

        System.out.println("Identical Workout Exercises:");
        for (IExercise ex : w2Exercises) {
            System.out.println("  " + ex.getName() + ", hashCode: " + ex.hashCode() + ", repsPerSet: " + ex.getRepsForAllSets().hashCode());
        }

        System.out.println("Exercise List Equality: " + w1Exercises.equals(w2Exercises));
        System.out.println("Workout hashCodes: " + w1.hashCode() + " vs. " + w2.hashCode());
    }

    @Test
    public void equalsFailsOnDifferentNamedWorkouts() {

        Workout differentWorkout = new Workout("Different Workout");

        Map<Integer, Integer> benchReps = new HashMap<>();
        benchReps.put(0, 10);
        benchReps.put(1, 10);
        benchReps.put(2, 8);
        differentWorkout.addExercise(new Exercise("Bench Press", 3, benchReps, 10, 65.00, Mode.DUMBBELL));

        Map<Integer, Integer> squatReps = new HashMap<>();
        squatReps.put(0, 12);
        squatReps.put(1, 10);
        squatReps.put(2, 10);
        differentWorkout.addExercise(new Exercise("Squat", 3, squatReps, 12, 185.00, Mode.BARBELL));

        Map<Integer, Integer> pullupReps = new HashMap<>();
        pullupReps.put(0, 15);
        pullupReps.put(1, 12);
        pullupReps.put(2, 12);
        differentWorkout.addExercise(new Exercise("Pull-ups", 3, pullupReps, 15, 0.00, Mode.BODYWEIGHT));

        Assert.assertNotEquals(testWorkout, differentWorkout);
    }

    @Test
    public void equalsFailsOnDifferentExerciseWorkouts() {

        Workout differentExercisesWorkout = new Workout("Test Workout");

        Map<Integer, Integer> deadliftReps = new HashMap<>();
        deadliftReps.put(0, 10);
        deadliftReps.put(1, 10);
        deadliftReps.put(2, 10);
        differentExercisesWorkout.addExercise(new Exercise("Deadlift", 3, deadliftReps, 10, 225.00, Mode.BARBELL));

        Assert.assertNotEquals(testWorkout, differentExercisesWorkout);
    }

    // two workouts will not have the same hashcode if their contents is not the same.
    @Test
    public void workoutsHashCodeDiffersWithContent() {

        // Same name as @before seup method workout
        Workout differentWorkout = new Workout("Test Workout");

        // Add different exercises
        Map<Integer, Integer> deadliftReps = new HashMap<>();
        deadliftReps.put(0, 10);
        deadliftReps.put(1, 10);
        deadliftReps.put(2, 10);
        differentWorkout.addExercise(new Exercise("Deadlift", 3, deadliftReps, 10, 225.00, Mode.BARBELL));

        Assert.assertNotEquals(testWorkout.hashCode(), differentWorkout.hashCode());
    }

    @Test
    public void workoutEqualsFailsWhenComparedToNullOrDifferentType() {

        Assert.assertNotEquals(testWorkout, null);
        Assert.assertNotEquals(testWorkout, new Object());
    }
}
