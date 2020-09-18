package dev.swayamraina.flashcard.storage.worker.file;

public enum Type {

    UNKNOWN,

    JSON,

    README,

    HTML;



    public String get () { return this.toString().toLowerCase(); }

}
