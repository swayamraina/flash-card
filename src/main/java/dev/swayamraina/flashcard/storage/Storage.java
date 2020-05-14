package dev.swayamraina.flashcard.storage;

import dev.swayamraina.flashcard.storage.worker.BloomFilter;
import dev.swayamraina.flashcard.storage.worker.ExternalFile;
import dev.swayamraina.flashcard.storage.worker.HashRing;
import dev.swayamraina.flashcard.storage.worker.L2Cache;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service public class Storage {


    private BloomFilter l1cache;
    private L2Cache l2cache;
    private HashRing hashRing;
    private ExternalFile file;


    @Autowired public Storage (
            BloomFilter l1cache,
            L2Cache l2cache,
            HashRing hashRing,
            ExternalFile file) {

        this.l1cache = l1cache;
        this.l2cache = l2cache;
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
            if (SCode.SAVED_TO_BLOOM_FILTER == code)
                code = l2cache.add(card.url(), today);
        }
        return code;
    }


    public boolean exists (String url, boolean deep) {
        boolean exists = l1cache.contains(url);
        if (exists) {
            exists = l2cache.exists(url);
            if (deep && !exists) exists = hashRing.exists(url);
        }
        return exists;
    }


    public List<String> get (int offset) { return l2cache.get(offset); }


}
