package dev.swayamraina.flashcard.utils;

import dev.swayamraina.flashcard.service.github.request.Committer;
import dev.swayamraina.flashcard.service.github.request.Request;
import dev.swayamraina.flashcard.storage.SCode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public static final String HEADER_AUTH_KEY = "Authorization";

    public static final String HEADER_AUTH_VALUE = "token %s";

    public static final String EMPTY = "";

    public static final String HYPHEN = "-";

    public static final String COMMA = ",";

    public static final String SPACE = " ";

    public static final String NEWLINE = "\n";

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
    public static final String RESOURCE_URL = "https://api.github.com/repos/%s/%s/contents/%s/%s/%s";

    public static final String HASH_RING_URL = "https://api.github.com/repos/%s/%s/contents/.db/hash/%s";

    public static final String BLOOM_FILTER_READ_URL = "https://api.github.com/repos/%s/%s/git/blobs/%s";

    public static final String BLOOM_FILTER_WRITE_URL = "https://api.github.com/repos/%s/%s/contents/.db/%s";

    public static final String BLOOM_SHA_URL = "https://api.github.com/repos/%s/%s/contents/.db/bloom-sha";

    public static final String DATE_FILE_FORMAT = "yyyy-MMMM-dd";

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat (DATE_FILE_FORMAT);

    public static final String SYNC_MESSAGE = "SYNCED on %s";

}
