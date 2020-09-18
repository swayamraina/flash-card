package dev.swayamraina.flashcard.storage.worker.file;

import dev.swayamraina.flashcard.service.github.Github;
import dev.swayamraina.flashcard.service.github.entity.Resource;
import dev.swayamraina.flashcard.service.github.request.Request;
import dev.swayamraina.flashcard.storage.SCode;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import dev.swayamraina.flashcard.web.routes.interceptor.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service public class ExternalFile {

    private Github github;
    private Config config;

    @Autowired public ExternalFile (Github github, Config config) {
        this.github = github;
        this.config = config;
    }

    public SCode add (FlashCard card, Date today) {
        Type t;
        try { t = Type.valueOf(config.format()); }
        catch (IllegalArgumentException e) { t = Type.JSON; }
        switch (t) {
            case JSON:      return addAsJson(card, today);
            case README:    throw new UnsupportedOperationException();
            case HTML:      throw new UnsupportedOperationException();
        }
        return SCode.ERROR;
    }


    private SCode addAsJson (FlashCard card, Date today) {
        boolean exists;
        String api = github.resourceUrl(today);
        Optional<Resource> resource = github.read(api);
        exists = resource.isPresent() && resource.get().exists();
        Request request = github.request(card, resource.get());

        if (request.invalid())
            return SCode.ERROR;

        resource = exists ? github.update(api, request) : github.create(api, request);
        return resource.isPresent() && resource.get().valid() ? SCode.SAVED_TO_FILE : SCode.ERROR;
    }

}
