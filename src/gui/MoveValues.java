package gui;

/**
 * Created by Eiras.
 */
public class MoveValues implements Comparable<MoveValues> {
    int number;
    int value;

    public MoveValues(int number, int value){
        this.number = number;
        this.value = value;
    }

    @Override
    public int compareTo(MoveValues o) {
        return (value < o.value) ? 1 : ((value > o.value) ? -1 : 0);
    }
}
