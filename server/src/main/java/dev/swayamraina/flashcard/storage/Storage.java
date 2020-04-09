package dev.swayamraina.flashcard.storage;

import dev.swayamraina.flashcard.storage.worker.GithubFile;
import dev.swayamraina.flashcard.storage.worker.MemoryCache;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service public class Storage {

    private MemoryCache l1cache;
    private GithubFile file;

    @Autowired public Storage (MemoryCache l1cache, GithubFile file) {
        this.l1cache = l1cache;
        this.file = file;
    }

    public SCode add (FlashCard card) {
        Date today = new Date();
        SCode code = file.add(card, today);
        if (SCode.SAVED_TO_FILE == code) {
            code = l1cache.add(card.url(), today);
        }
        return code;
    }

}
