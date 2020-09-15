package dev.swayamraina.flashcard.storage;

import dev.swayamraina.flashcard.storage.worker.BloomFilter;
import dev.swayamraina.flashcard.storage.worker.file.ExternalFile;
import dev.swayamraina.flashcard.storage.worker.HashRing;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service public class Storage {


    private BloomFilter l1cache;
    private HashRing hashRing;
    private ExternalFile file;


    @Autowired public Storage (
            BloomFilter l1cache,
            HashRing hashRing,
            ExternalFile file) {

        this.l1cache = l1cache;
        this.hashRing = hashRing;
        this.file = file;
    }


    public SCode add (FlashCard card) {
        SCode code = SCode.SAVED;
        if (!exists(card.url(), true)) {
            Date today = new Date();
            code = file.add(card, today);
            if (SCode.SAVED_TO_FILE == code)
                code = hashRing.add(card.url());
            if (SCode.SAVED_TO_HASH_RING == code)
                code = l1cache.add(card.url());
        }
        return code;
    }


    public boolean exists (String url, boolean deep) {
        boolean exists = l1cache.contains(url);
        if (deep && !exists)
            exists = hashRing.exists(url);

        return exists;
    }


}
