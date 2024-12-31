package model;

public class SetReps implements ISetReps {

    private final int setNumber;

    private final int reps;

    public SetReps(int setNumber, int reps) {
        this.setNumber = setNumber;
        this.reps = reps;
    }

    @Override
    public int getSetNumber() {
        return this.setNumber;
    }

    @Override
    public int getReps() {
        return this.reps;
    }

    @Override
    public int hashcode() {
        return 31 * Integer.hashCode(setNumber) + Integer.hashCode(reps);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SetReps setReps = (SetReps) object;
        return setNumber == setReps.setNumber && reps == setReps.reps;
    }
}
