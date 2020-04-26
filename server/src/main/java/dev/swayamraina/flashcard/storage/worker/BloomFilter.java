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

import static dev.swayamraina.flashcard.utils.Constants.COMMA;
import static dev.swayamraina.flashcard.utils.Constants.EMPTY;

@Service public class BloomFilter {


    private static final String bloom = "bloom";
    private static final int seed1 = 123456789;
    private static final int seed2 = 987654321;
    private static final int seed3 = 192837465;
    private static final long time = 24 * 60 * 60 * 1000;
    private static final int buckets = 512;
    private static final Charset utf8 = StandardCharsets.UTF_8;


    private Github github;
    private Container container;
    private HashFunction function1, function2, function3;
    private Thread daemon;

    private Runnable task = () -> {
        while (true) {
            try { Thread.sleep(time); } catch (InterruptedException e) {}
            this.serialize();
        }
    };


    @Autowired public BloomFilter (Github github) {
        function1 = Hashing.murmur3_32(seed1);
        function2 = Hashing.murmur3_32(seed2);
        function3 = Hashing.murmur3_32(seed3);
        this.github = github;
        this.container = this.deserialize();

        this.daemon = new Thread(task);
        this.daemon.start();
    }


    synchronized public SCode add (String url) {
        set(hash(function1, url));
        set(hash(function2, url));
        set(hash(function3, url));
        return SCode.SAVED_TO_BLOOM_FILTER;
    }


    synchronized public boolean contains (String url) {
        return get(hash(function1, url)) &&
                    get(hash(function2, url)) &&
                        get(hash(function3, url));
    }


    synchronized public SCode serialize () {
        String sha = null;
        String api = github.bloomShaUrl();
        Optional<Resource> shaResource = github.read(api);
        boolean exists = shaResource.isPresent() && shaResource.get().exists();

        api = github.bloomUrl(bloom, Boolean.FALSE);
        String content = container.serialize();
        if (exists) sha = Utils.b64decode(shaResource.get().content());
        Request request = github.request(content, sha);
        Optional<Resource> resource = exists ? github.update(api, request) : github.create(api, request);

        if (!resource.isPresent() || !resource.get().valid())
            return SCode.ERROR;

        api = github.bloomShaUrl();
        content = resource.get().sha();
        if (exists) sha = shaResource.get().sha();
        request = github.request(content, sha);
        resource = exists ? github.update(api, request) : github.create(api, request);

        return resource.isPresent() && resource.get().valid() ? SCode.SAVED : SCode.ERROR;
    }


    private Container deserialize () {
        String api = github.bloomShaUrl();
        Optional<Resource> resource = github.read(api);
        boolean exists = resource.isPresent() && resource.get().exists();
        if (exists) {
            String name = Utils.b64decode(resource.get().content());
            api = github.bloomUrl(name, Boolean.TRUE);
            resource = github.read(api);
        }
        return new Container(resource);
    }


    private Pair hash (HashFunction function, String url) {
        int hash = Math.abs(function.hashString(url, utf8).asInt());
        int holder = (int) Math.floor(hash/buckets);
        int index = hash % buckets;
        return new Pair(holder, index);
    }


    private void set (Pair pair) {
        int value = container.get(pair.holder);
        value |= (1 << pair.index);
        container.set(pair.holder, value);
    }


    private boolean get (Pair pair) {
        int value = container.get(pair.holder);
        return 0 != (value & (1 << pair.index));
    }









    private static class Pair {
        private int holder, index;
        public Pair (int holder, int index) { this.holder = holder;  this.index = index; }
    }


    private static class Container {
        private static final int size = Integer.MAX_VALUE / buckets;    // 64 MB
        private volatile int container[];
        private int get (int i) { return container[i]; }
        private void set (int i, int v) { container[i] = v; }

        public Container (Optional<Resource> resource) {
            String content = EMPTY;
            if (resource.isPresent() && resource.get().exists())
                content = Utils.b64decode(resource.get().content());
            this.container = this.deserialize(content);
        }

        private String serialize () {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < container.length; i++) {
                sb.append(container[i]);
                if (i+1 < container.length) sb.append(COMMA);
            }
            return sb.toString();
        }

        private int[] deserialize (String content) {
            int temp[] = new int[size];
            String data[] = content.split(COMMA);
            for (int i=0; data.length>1 && i<size; i++)
                temp[i] = Integer.parseInt(data[i]);
            return temp;
        }
    }


}
