package dev.swayamraina.flashcard.storage.entity.vo;

import dev.swayamraina.flashcard.storage.entity.CircularBuffer;

public class Month {

    private static final int STORAGE_SIZE = 31;

    private CircularBuffer<Day> month;
    public CircularBuffer<Day> get () { return month; }
    public void add (Day day) { month.add(day); }

    public Month () { month = new CircularBuffer<>(STORAGE_SIZE); }

}
