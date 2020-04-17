package dev.swayamraina.flashcard.core;


import java.util.Objects;

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
        if (Objects.nonNull(element)) {
            data[tail] = element;
            tail = (tail+1)%size;
        }
    }


    public E poll () {
        E element = (E) data[head];
        if (Objects.nonNull(element)) {
            data[head] = null;
            head = (head+1)%size;
        }
        return element;
    }


    public boolean full () { return (size-1 == tail-head) || (1 == head-tail); }
    public boolean notFull () { return !full(); }


    public boolean empty () { return (0 == head-tail); }
    public boolean notEmpty () { return !empty(); }


}
