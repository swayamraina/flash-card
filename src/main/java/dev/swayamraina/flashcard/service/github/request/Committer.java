package dev.swayamraina.flashcard.service.github.request;

public class Committer {

    private String name;
    public String getName() { return name; }

    private String email;
    public String getEmail() { return email; }


    private Committer () {}

    public Committer (String name, String email) {
        this.name = name;
        this.email = email;
    }

}
