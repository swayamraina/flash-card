package dev.swayamraina.flashcard.core;

import java.util.Objects;

public class Queue <E> {


    private static final int QUEUE_SIZE = 16;


    private CircularQueue<E> current;
    public CircularQueue<E> current () { return this.current; }

    private CircularQueue<E> reserved;
    public CircularQueue<E> reserved () { return this.reserved; }

    private CircularQueue<E> failed;
    public CircularQueue<E> failed () { return this.failed; }


    public Queue () {
        this.current = new CircularQueue(QUEUE_SIZE);
        this.reserved = new CircularQueue(QUEUE_SIZE);
        this.failed = new CircularQueue(QUEUE_SIZE);
    }


    synchronized public QCode add (E element) {
        if (current.full() && reserved.full()) return QCode.RESERVE_FULL;
        CircularQueue<E> queue = current.notFull() ? current : reserved;
        queue.add(element);
        return QCode.SUCCESS;
    }


    synchronized public E poll () {
        E ec = null, er = null, ef = null;
        if (current.notEmpty()) {
            ec = current.poll();
            er = reserved.poll();
            ef = failed.poll();
            if (Objects.nonNull(er)) current.add(er);
            if (Objects.nonNull(ef)) reserved.add(ef);
        }
        return ec;
    }


}
