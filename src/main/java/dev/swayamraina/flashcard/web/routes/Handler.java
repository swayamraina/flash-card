package dev.swayamraina.flashcard.web.routes;

import dev.swayamraina.flashcard.web.response.base.Response;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@CrossOrigin
@RequestMapping (Routes.BASE_URL)
@RestController public class Handler {


    private static final Response<Boolean> FAILURE = new Response<>(Boolean.FALSE);
    private static final Response<Boolean> SUCCESS = new Response<>(Boolean.TRUE);


    private Orchestrator orchestrator;


    @Autowired public Handler (Orchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }


    @PostMapping (Routes.APP.SAVE_LINK) public Response<Boolean> add (
            @RequestBody FlashCard card) {

        if (Objects.isNull(card) || Objects.isNull(card.url()))
            return FAILURE;

        boolean added = orchestrator.handle(card);
        return added ? SUCCESS : FAILURE;
    }


    @GetMapping (Routes.APP.LINK_EXISTS) public Response<Boolean> exists (
            @RequestParam ("url") String url) {

        if (StringUtils.isEmpty(url))
            return FAILURE;

        boolean exists = orchestrator.exists(url);
        return exists ? SUCCESS : FAILURE;
    }


}
