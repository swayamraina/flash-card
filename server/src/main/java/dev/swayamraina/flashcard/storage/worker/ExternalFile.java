package dev.swayamraina.flashcard.storage.worker;

import dev.swayamraina.flashcard.service.github.Github;
import dev.swayamraina.flashcard.service.github.entity.Resource;
import dev.swayamraina.flashcard.service.github.request.Request;
import dev.swayamraina.flashcard.storage.SCode;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service public class ExternalFile {

    private Github github;

    @Autowired public ExternalFile (Github github) {
        this.github = github;
    }

    public SCode add (FlashCard card, Date today) {
        boolean valid, exists;
        String api;
        api = github.url(today);
        Optional<Resource> resource = github.read(api);
        valid = resource.isPresent() && resource.get().valid();
        if (valid) {
            exists = resource.get().exists();
            Request request = github.request(card, resource.get());
            if (request.invalid()) return SCode.ERROR;
            resource = exists ? github.update(api, request) : github.create(api, request);
        }
        return resource.isPresent() && resource.get().valid() ? SCode.SAVED_TO_FILE : SCode.ERROR;
    }

}
