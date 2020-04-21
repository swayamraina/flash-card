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

    public E get (int index) { return (E) data[index]; }

    public boolean exists (E element) {
        boolean exists = false;
        for (Object d : data) {
            if (null == d) continue;
            E e = (E) d;
            if (e.equals(element)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public int pointer () { return pointer; }
    public int size () { return size; }

}
