package dev.swayamraina.flashcard.storage.worker;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import dev.swayamraina.flashcard.storage.SCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.List;


@Service public class L1Cache {

    private BloomFilter<String> memory;

    @Autowired public L1Cache() {
        memory = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), Integer.MAX_VALUE);
    }

    public void addAll (List<String> urls) {
        if (CollectionUtils.isEmpty(urls)) return;
        for (String url : urls) {
            this.add(url);
        }
    }

    public SCode add (String url) {
        memory.put(url);
        return SCode.SAVED_TO_BLOOM_FILTER;
    }

    public boolean exists (String url) {
        return memory.mightContain(url);
    }

}
