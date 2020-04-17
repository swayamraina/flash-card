package dev.swayamraina.flashcard.web.response.vo;

import java.util.List;



public class FlashCard {

    private String url;
    public String url () { return url; }
    public String getUrl () { return url; }

    private List<String> tags;
    public List<String> tags () { return tags; }
    public List<String> getTags () { return tags; }



    private FlashCard () {}

    public FlashCard (String url, List<String> tags) {
        this.url = url;
        this.tags = tags;
    }

}
