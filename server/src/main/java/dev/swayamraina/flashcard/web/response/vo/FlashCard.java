package dev.swayamraina.flashcard.web.response.vo;

import java.util.List;



public class FlashCard {

    private String url;
    public String url () { return url; }

    private List<String> tags;
    public List<String> tags () { return tags; }



    private FlashCard () {}

    public FlashCard (String url, List<String> tags) {
        this.url = url;
        this.tags = tags;
    }

}
