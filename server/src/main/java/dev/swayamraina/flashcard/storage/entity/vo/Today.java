package dev.swayamraina.flashcard.storage.entity.vo;

import java.util.HashSet;
import java.util.Set;

public class Today {

    private Set<String> today;
    public Set<String> get () { return today; }
    public void add (String url) { today.add(url); }

    public Today () { today = new HashSet<>(); }

}
