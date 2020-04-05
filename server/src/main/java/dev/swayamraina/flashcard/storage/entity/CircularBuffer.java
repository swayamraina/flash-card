package dev.swayamraina.flashcard.storage.entity;

public class CircularBuffer <E> {

    private int size;
    private Object[] data;
    private int pointer;

    public CircularBuffer (int size) {
        this.size = size;
        this.pointer = -1;
        this.data = new Object[size];
    }

    public void add (E element) {
        int index = (pointer++) % size;
        E previous = (E) data[index];
        data[index] = element;
        previous = null;
    }

}
