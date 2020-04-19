package dev.swayamraina.flashcard.storage;

import dev.swayamraina.flashcard.storage.worker.ExternalFile;
import dev.swayamraina.flashcard.storage.worker.L2Cache;
import dev.swayamraina.flashcard.storage.worker.L1Cache;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service public class Storage {

    private L1Cache l1cache;
    private L2Cache l2cache;
    private ExternalFile file;

    @Autowired public Storage (
            L1Cache l1cache,
            L2Cache l2cache,
            ExternalFile file) {

        this.l1cache = l1cache;
        this.l2cache = l2cache;
        this.file = file;
    }

    public SCode add (FlashCard card) {
        Date today = new Date();
        SCode code = file.add(card, today);
        if (SCode.SAVED_TO_FILE == code) {
            l1cache.add(card.url());
            code = l2cache.add(card.url(), today);
        }
        return code;
    }

    public boolean exists (String url) {
        boolean exists = l1cache.exists(url);
        if (exists) exists = l2cache.exists(url);
        return exists;
    }

}
