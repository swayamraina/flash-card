package dev.swayamraina.flashcard.service.github.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.swayamraina.flashcard.storage.SCode;

@JsonInclude (JsonInclude.Include.NON_NULL)
public class Request {

    private SCode valid;
    public boolean invalid () { return SCode.ERROR == this.valid; }

    private String content;
    public String getContent() { return content; }

    private String sha;
    public String getSha() { return sha; }

    private String message;
    public String getMessage() { return message; }

    private Committer committer;
    public Committer getCommitter() { return committer; }


    private Request () {}

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
