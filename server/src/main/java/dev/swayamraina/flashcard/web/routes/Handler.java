package dev.swayamraina.flashcard.web.routes;

import dev.swayamraina.flashcard.web.response.base.Response;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController public class Handler {


    private static final Response<Boolean> FAILURE = new Response<>(Boolean.FALSE);
    private static final Response<Boolean> SUCCESS = new Response<>(Boolean.TRUE);


    private Orchestrator orchestrator;


    @Autowired public Handler (Orchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }


    @PostMapping public Response<Boolean> add (@RequestBody FlashCard card) {
        if (Objects.isNull(card) || Objects.isNull(card.url()))
            return FAILURE;

        boolean added = orchestrator.handle(card);
        return added ? SUCCESS : FAILURE;
    }


    @GetMapping public Response<List<String>> get () {
        orchestrator.get();
        return null;
    }


    @GetMapping public Response<Boolean> exists (@RequestParam String url) {
        if (StringUtils.isEmpty(url))
            return FAILURE;

        boolean exists = orchestrator.exists();
        return exists ? SUCCESS : FAILURE;
    }


}
