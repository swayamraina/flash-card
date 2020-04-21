package dev.swayamraina.flashcard.storage.worker;

import dev.swayamraina.flashcard.storage.SCode;
import dev.swayamraina.flashcard.storage.entity.CircularBuffer;
import dev.swayamraina.flashcard.storage.entity.Memory;
import dev.swayamraina.flashcard.storage.entity.vo.Day;
import dev.swayamraina.flashcard.storage.entity.vo.Month;
import dev.swayamraina.flashcard.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service public class L2Cache {

    private Memory memory;
    public Memory memory () { return memory; }


    @Autowired L2Cache () {
        this.memory = new Memory();
    }


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
        Day prevDay = memory.dayHolder();
        memory.month().add(prevDay);
        if (Utils.isNextMonth(memory.calendar(), today)) {
            Month prevMonth = memory.monthHolder();
            memory.year().add(prevMonth);
        }
        memory.today().clear();
    }

    public boolean exists (String url) {
        boolean exists = memory.today().contains(url);
        if (!exists) memory.monthHolder().exists(url);
        if (!exists) memory.yearHolder().exists(url);
        return exists;
    }

    public List<String> get (int offset) {
        List<String> urls = new ArrayList<>();

        if (0 == offset)
            urls.addAll(memory.today());

        if (31 > offset && offset > 0) {
            CircularBuffer<Day> month = memory.month();
            int pointer = month.pointer();
            int query = pointer - offset;
            if (0 > query) query += month.size();
            urls.addAll(month.get(query).get());
        }

        return urls;
    }

}
