package dev.swayamraina.flashcard.storage.worker;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import dev.swayamraina.flashcard.service.github.Github;
import dev.swayamraina.flashcard.service.github.entity.Resource;
import dev.swayamraina.flashcard.service.github.request.Request;
import dev.swayamraina.flashcard.storage.SCode;
import dev.swayamraina.flashcard.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static dev.swayamraina.flashcard.utils.Constants.NEWLINE;

@Service public class HashRing {

    private static final int seed = 123456789;
    private static final Charset utf8 = StandardCharsets.UTF_8;

    private HashFunction function;
    private Github github;

    @Autowired public HashRing (Github github)  {
        function = Hashing.murmur3_32(seed);
        this.github = github;
    }

    public SCode add (String url) {
        boolean valid, exists;
        int hash = hash(url);
        String api = github.hashRingUrl(hash);
        Optional<Resource> resource = github.read(api);
        valid = resource.isPresent() && resource.get().valid();
        if (valid) {
            exists = resource.get().exists();
            Request request = github.request(url, resource.get());
            if (request.invalid()) return SCode.ERROR;
            resource = exists ? github.update(api, request) : github.create(api, request);
        }
        return resource.isPresent() && resource.get().valid() ? SCode.SAVED_TO_HASH_RING : SCode.ERROR;
    }

    public boolean exists (String url) {
        boolean exists = false;
        int hash = hash(url);
        String api = github.hashRingUrl(hash);
        Optional<Resource> resource = github.read(api);
        if (resource.isPresent() && resource.get().valid()) {
            String content[] = Utils.b64decode(resource.get().content()).split(NEWLINE);
            for (String c : content) {
                if (c.equals(url)) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

    private int hash (String url) {
        return Math.abs(function.hashString(url, utf8).asInt());
    }

}
