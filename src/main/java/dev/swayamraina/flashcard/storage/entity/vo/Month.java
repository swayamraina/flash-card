package dev.swayamraina.flashcard.storage.entity.vo;

import dev.swayamraina.flashcard.storage.entity.CircularBuffer;

import java.util.Objects;

public class Month {

    private static final int STORAGE_SIZE = 31;

    private CircularBuffer<Day> month;
    public CircularBuffer<Day> get () { return month; }
    public void add (Day day) { month.add(day); }

    public Month () { month = new CircularBuffer<>(STORAGE_SIZE); }

    public boolean exists (String url) {
        boolean exists = false;
        for (int i=0; i<STORAGE_SIZE; i++) {
            if (Objects.nonNull(month.get(i))) {
                exists = month.get(i).get().contains(url);
            }
            if (exists) break;
        }
        return exists;
    }

}
