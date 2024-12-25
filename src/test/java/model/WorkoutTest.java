package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class WorkoutTest {

    private Workout testWorkout;

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
        testWorkout.addExercise(new Exercise("Pull-ups", 3, pullupReps, 15, 0.0, Mode.BODYWEIGHT));
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
}
