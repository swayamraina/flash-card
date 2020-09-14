package dev.swayamraina.flashcard.service.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.swayamraina.flashcard.service.github.entity.GithubConfig;
import dev.swayamraina.flashcard.service.github.entity.Resource;
import dev.swayamraina.flashcard.service.github.entity.ResourceResponse;
import dev.swayamraina.flashcard.service.github.request.Committer;
import dev.swayamraina.flashcard.service.github.request.Request;
import dev.swayamraina.flashcard.service.github.response.Response;
import dev.swayamraina.flashcard.storage.SCode;
import dev.swayamraina.flashcard.utils.Utils;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static dev.swayamraina.flashcard.utils.Constants.*;

@Service public class Github {


    public static final Resource INVALID_RESOURCE = new Resource (GCode.INVALID, EMPTY, EMPTY);
    public static final Resource NO_RESOURCE = new Resource (GCode.DOES_NOT_EXISTS, EMPTY, EMPTY);


    private GithubConfig config;
    private Committer committer;
    private ObjectMapper mapper;


    @Autowired public Github (GithubConfig config) {
        this.config = config;
        this.committer = new Committer(config.username(), config.email());
        this.mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public Optional<Resource> create (String url, Request data) { return submit(url, data, Boolean.TRUE); }
    public Optional<Resource> update (String url, Request data) { return submit(url, data, Boolean.FALSE); }


    public Optional<Resource> read (String url) {
        Resource resource;
        try {
            resource = new RestTemplate().getForEntity(url, Resource.class).getBody();
        } catch (RestClientException rce) {
            resource = NO_RESOURCE;
        }
        return Optional.of(resource);
    }


    private Optional<Resource> submit (String url, Request data, boolean create) {
        Resource resource;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HEADER_AUTH_KEY, String.format(HEADER_AUTH_VALUE, config.token()));
            HttpEntity<Request> entity = new HttpEntity<>(data, headers);
            ResourceResponse response = new RestTemplate().exchange (
                    url,
                    HttpMethod.PUT,
                    entity,
                    ResourceResponse.class
            ).getBody();
            resource = new Resource (
                    create ? GCode.CREATED : GCode.UPDATED,
                    response.sha(),
                    EMPTY
            );
        } catch (RestClientException rce) {
            resource = INVALID_RESOURCE;
        }
        return Optional.of(resource);
    }


    public String resourceUrl (Date today) {
        String[] vars = DATE_FORMATTER.format(today).split(HYPHEN);
        return String.format (
                RESOURCE_URL,
                config.username(),
                config.repo(),
                vars[0],
                vars[1],
                vars[2]
        );
    }


    public String hashRingUrl (int hash) {
        return String.format (
                HASH_RING_URL,
                config.username(),
                config.repo(),
                hash
        );
    }


    public String bloomUrl (String name, boolean read) {
        return String.format (
                read ? BLOOM_FILTER_READ_URL : BLOOM_FILTER_WRITE_URL,
                config.username(),
                config.repo(),
                name
        );
    }


    public String bloomShaUrl () {
        return String.format (
                BLOOM_SHA_URL,
                config.username(),
                config.repo()
        );
    }


    public Request request (FlashCard card, Resource resource) {
        String content, sha = null;
        Response response = null;
        if (resource.exists()) {
            content = Utils.b64decode(resource.content());
            try {
                response = mapper.readValue(content, Response.class);
            } catch (JsonProcessingException e) {
                return INVALID_REQUEST;
            }
            sha = resource.sha();
        }
        if (Objects.isNull(response)) response = new Response(new ArrayList<>());
        response.cards().add(card);

        try {
            content = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            return INVALID_REQUEST;
        }

        return new Request (
                SCode.UNKNOWN,
                Utils.b64encode(content),
                sha,
                Utils.commitMessage(response),
                committer
        );
    }


    public Request request (String url, Resource resource)  {
        String content = url, sha = null;
        if (resource.exists()) {
            content += NEWLINE + Utils.b64decode(resource.content());
            sha = resource.sha();
        }
        return new Request (
                SCode.UNKNOWN,
                Utils.b64encode(content),
                sha,
                url,
                committer
        );
    }


    public Request request (String content, String sha) {
        return new Request (
                SCode.UNKNOWN,
                Utils.b64encode(content),
                sha,
                SYNC_MESSAGE.format(new Date().toString()),
                committer
        );
    }

}
