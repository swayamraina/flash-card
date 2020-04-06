package dev.swayamraina.flashcard.storage.worker;

import dev.swayamraina.flashcard.storage.SCode;
import dev.swayamraina.flashcard.storage.entity.Memory;
import dev.swayamraina.flashcard.storage.entity.vo.Day;
import dev.swayamraina.flashcard.storage.entity.vo.Month;
import dev.swayamraina.flashcard.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service public class MemoryCache {

    private Memory memory;
    public Memory memory () { return memory; }


    public SCode add (String url, Date today) {
        if (Utils.isNewDay(memory.calendar(), today)) {
            rollover(today);
        }
        addToDay(url);
        return SCode.SAVED_TO_CACHE;
    }

    private void addToDay (String url) {
        memory.today().add(url);
    }

    public void rollover (Date today) {
        Day prevDay = memory.today();
        memory.month().add(prevDay);
        if (Utils.isNextMonth(memory.calendar(), today)) {
            Month prevMonth = memory.month();
            memory.year().add(prevMonth);
        }
        memory.today().flush();
    }

}
