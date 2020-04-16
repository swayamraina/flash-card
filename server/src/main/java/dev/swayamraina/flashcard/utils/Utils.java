package dev.swayamraina.flashcard.utils;

import dev.swayamraina.flashcard.service.github.response.Response;
import dev.swayamraina.flashcard.web.response.vo.FlashCard;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import static dev.swayamraina.flashcard.utils.Constants.COMMA;


public class Utils {

    public static boolean isNewDay (Calendar calendar, Date date) {
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int incomingDay = date.getDay();
        return incomingDay != currentDay;
    }

    public static boolean isNextMonth (Calendar calendar, Date date) {
        int currentMonth = calendar.get(Calendar.MONTH);
        int incomingMonth = date.getMonth();
        return incomingMonth > currentMonth;
    }

    public static String b64encode (String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String b64decode (String b64data) {
        return new String(Base64.getDecoder().decode(b64data));
    }

    public static String commitMessage (Response response) {
        StringBuilder sb = new StringBuilder();
        for (FlashCard fc : response.cards()) {
            for (String tag : fc.tags()) {
                sb.append(tag).append(COMMA);
            }
        }
        return sb.toString();
    }

}
