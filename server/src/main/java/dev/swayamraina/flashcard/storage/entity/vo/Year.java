package dev.swayamraina.flashcard.storage.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class Year {

    private static final int STORAGE_SIZE = 12;

    private int pointer;
    public int pointer () { return pointer; }

    private List<Month> year;
    public List<Month> year () { return year; }

    public Year () {
        pointer = 0;
        year = new ArrayList<>(STORAGE_SIZE);
    }

}
