package dev.swayamraina.flashcard.web.routes;

import dev.swayamraina.flashcard.core.QCode;
import dev.swayamraina.flashcard.core.QueueHandler;
import dev.swayamraina.flashcard.storage.Storage;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service public class Orchestrator {

    private QueueHandler<FlashCard> queueHandler;
    private Storage storage;

    @Autowired public Orchestrator (
            QueueHandler<FlashCard> queueHandler,
            Storage storage) {

        this.storage = storage;
        this.queueHandler = queueHandler;
    }

    public boolean handle (FlashCard card) {
        QCode code = queueHandler.handle(card);
        return QCode.SUCCESS == code;
    }


    public void get () {

    }


    public Boolean exists () {
        return null;
    }


}
