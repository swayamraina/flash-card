package dev.swayamraina.flashcard.core;

public class Pendulum {


    private static final int MAX = 1000 * 60 * 8;
    private static final int MIN = 1000 * 60 * 1;

    private boolean flip;
    private int time;


    synchronized public int time () {
        if (MAX == time || MIN == time)
            flip = !flip;

        time = flip ? time/2 : time*2;
        return time;
    }


    public Pendulum () {
        this.time = MAX;
        this.flip = false;
    }


}
