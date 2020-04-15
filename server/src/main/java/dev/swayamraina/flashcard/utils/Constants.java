package dev.swayamraina.flashcard.utils;

import dev.swayamraina.flashcard.service.github.GCode;
import dev.swayamraina.flashcard.service.github.entity.Resource;
import dev.swayamraina.flashcard.service.github.request.Committer;
import dev.swayamraina.flashcard.service.github.request.Request;
import dev.swayamraina.flashcard.storage.SCode;

import java.text.SimpleDateFormat;

public class Constants {

    public static final String HEADER_AUTH_KEY = "Authorization";

    public static final String HEADER_AUTH_VALUE = "token %s";

    public static final String EMPTY = "";

    public static final String HYPHEN = "-";

    public static final String COMMA = ", ";

    public static final Resource INVALID_RESOURCE = new Resource (GCode.INVALID, EMPTY, EMPTY);

    public static final Committer UNKNOWN_COMMITTER = new Committer (EMPTY, EMPTY);

    public static final Request INVALID_REQUEST = new Request (SCode.ERROR, EMPTY, EMPTY, EMPTY, UNKNOWN_COMMITTER);

    /**
     *
     *
     * placeholders,
     *
     *      1. user name
     *      2. repo name
     *      3. year (YYYY)
     *      4. month name (MMMM)
     *      5. date (DD)
     **/
    public static final String RESOURCE_URL = "https://api.github.com/repos/%s/%s/contents/%s/%s/FC-%s.json";

    public static final String DATE_FILE_FORMAT = "yyyy-MMMM-dd";

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat (DATE_FILE_FORMAT);

}
