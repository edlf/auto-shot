package gui;

import com.sun.istack.internal.NotNull;

/**
 * Eduardo Fernandes
 * Filipe Eiras
 */
class MoveValues implements Comparable<MoveValues> {
    @NotNull
    private final int number;
    @NotNull
    private final int value;

    public MoveValues(@NotNull int number, @NotNull int value) {
        this.number = number;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(@NotNull MoveValues o) {
        return (value < o.value) ? 1 : ((value > o.value) ? -1 : 0);
    }
}
