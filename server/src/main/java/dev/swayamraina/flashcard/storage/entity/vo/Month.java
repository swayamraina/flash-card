package dev.swayamraina.flashcard.storage.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class Month {

    private static final int STORAGE_SIZE = 31;

    private int pointer;
    public int pointer () { return pointer; }

    private List<List<String>> month;
    public List<List<String>> get () { return month; }

    public Month () {
        pointer = 0;
        month = new ArrayList<>(STORAGE_SIZE);
        for (int i=0; i < STORAGE_SIZE; i++)
            month.add(new ArrayList<>());
    }

}
