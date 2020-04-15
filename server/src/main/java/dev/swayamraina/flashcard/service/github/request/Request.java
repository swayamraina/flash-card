package dev.swayamraina.flashcard.service.github.request;

import dev.swayamraina.flashcard.storage.SCode;

public class Request {

    private SCode valid;
    public boolean invalid () { return SCode.ERROR == this.valid; }

    private String content;
    private String sha;
    private String message;
    private Committer committer;

    public Request (
            SCode valid,
            String content,
            String sha,
            String message,
            Committer committer) {

        this.valid = valid;
        this.content = content;
        this.sha = sha;
        this.message = message;
        this.committer = committer;
    }

}
