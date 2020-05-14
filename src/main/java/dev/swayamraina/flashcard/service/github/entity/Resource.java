package dev.swayamraina.flashcard.service.github.entity;

import dev.swayamraina.flashcard.service.github.GCode;

public class Resource {

    private GCode valid;
    public boolean invalid () { return GCode.INVALID == this.valid; }
    public boolean valid () { return GCode.INVALID != this.valid; }
    public boolean exists () { return GCode.EXISTS == this.valid; }

    private String sha;
    public String sha () { return sha; }
    public String getSha() { return sha; }

    private String content;
    public String content () { return content; }
    public String getContent() { return content; }


    private Resource () { this.valid = GCode.EXISTS; }

    public Resource (GCode valid, String sha, String content) {
        this.valid = valid;
        this.sha = sha;
        this.content = content;
    }

}
