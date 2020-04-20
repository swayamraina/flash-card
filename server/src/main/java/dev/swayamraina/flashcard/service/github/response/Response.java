package dev.swayamraina.flashcard.service.github.response;

import dev.swayamraina.flashcard.web.response.vo.FlashCard;

import java.util.List;

public class Response {

    private List<FlashCard> cards;
    public List<FlashCard> cards () { return cards; }
    public List<FlashCard> getCards () { return cards; }


    private Response () {}

    public Response (List<FlashCard> cards) { this.cards = cards; }

}
