package dev.swayamraina.flashcard.storage.entity.vo;

import java.util.HashSet;
import java.util.Set;

public class Day {

    private Set<String> day;
    public Set<String> get () { return day; }
    public void add (String url) { day.add(url); }
    public void flush () { day.clear(); }

    public Day () { day = new HashSet<>(); }

}
