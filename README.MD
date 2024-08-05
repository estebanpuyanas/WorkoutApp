**Workout App by Esteban Puyana**
-

This java application serves as a workout tracker and data analytics tool. 
The application allows the user to: 
- Create, modify, and delete workout routines. 
- Create, modify, and delete workouts within a routine.
- Create modify, and delete exercise within a workout. 

**Application Structure:**
-

The application uses the model-view-controller design. 
The model is responsible for the logic functionality of the application. 
The controller is responsible for the information flow control (I/O) and updates the view and model correspondigly.
The view is responsible for the display of the application and all the relevant information.



Below is a detailed breakdown of the components of each module of the application.

Model Interfaces:
- 

- IExercise: Represents and exercise. It includes methods such as:
  1. createExercise();
  2. deleteExercise();
  3. updateWeight();
  4. getWeight();
  5. getSets();
  6. updateSets();
  7. getMode();
  8. setMode();
- IWorkout: Represents a workout, which is composed of at least one exercise. It includes methods such as:
  1. addExercise();
  2. removeExercise();
  3. deleteExercise();
  4. editExercise();
  5. getExerciseList();
- IRoutine: Represents a workout routine, which is composed of at least one workout. It includes methods such as:
  1. createRoutine();
  2. deleteRoutine();
  3. editRoutine();
  


