package dev.swayamraina.flashcard.core;


public class CircularQueue <E> {

    private int size;
    private Object[] data;
    private int head, tail;


    public CircularQueue (int size) {
        this.size = size;
        this.head = 0;
        this.tail = 0;
        this.data = new Object[size];
    }


    public void add (E element) {
        data[tail++] = element;
    }

    public E poll () {
        E element = (E) data[head];
        head++;
        return element;
    }

    public boolean full () { return Math.abs(head-tail) == 1; }

    public boolean notFull () { return !full(); }

    public boolean empty () { return Math.abs(head-tail) == 0; }

    public boolean notEmpty () { return !empty(); }


}
