package model;

import java.lang.reflect.Type;
import java.util.List;

public interface IWorkoutPlan {
    String getPlanID();
    void setPlanID(String planID);
    String getUserID();
    void setUserID(String userID);
    String getName();
    void setName(String name);
    List<IExercise> getWorkouts();
    void setWorkouts(List<IExercise> workouts);

}
