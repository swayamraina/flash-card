package dev.swayamraina.flashcard.storage.entity.vo;

import dev.swayamraina.flashcard.storage.entity.CircularBuffer;

import java.util.Objects;

public class Year {

    private static final int STORAGE_SIZE = 12;

    private CircularBuffer<Month> year;
    public CircularBuffer<Month> get () { return year; }
    public void add (Month month) { year.add(month); }

    public Year () { year = new CircularBuffer<>(STORAGE_SIZE); }

    public boolean exists (String url) {
        boolean exists = false;
        for (int i=0; i<STORAGE_SIZE; i++) {
            if (Objects.nonNull(year.get(i))) {
                exists = year.get(i).exists(url);
            }
            if (exists) break;
        }
        return exists;
    }

}
