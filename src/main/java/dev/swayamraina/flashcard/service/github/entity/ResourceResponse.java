package dev.swayamraina.flashcard.service.github.entity;

public class ResourceResponse {

    private Content content;
    public Content getContent() { return content; }
    public String sha () { return getContent().getSha(); }

    private static class Content {
        private String sha;
        public String getSha() { return sha; }
    }

}
