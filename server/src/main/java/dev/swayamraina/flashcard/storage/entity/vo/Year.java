package dev.swayamraina.flashcard.storage.entity.vo;

import dev.swayamraina.flashcard.storage.entity.CircularBuffer;

public class Year {

    private static final int STORAGE_SIZE = 12;

    private CircularBuffer<Month> year;
    public CircularBuffer<Month> get () { return year; }
    public void add (Month month) { year.add(month); }

    public Year () { year = new CircularBuffer<>(STORAGE_SIZE); }

}
