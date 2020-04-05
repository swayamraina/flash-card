package dev.swayamraina.flashcard.storage.entity;

import dev.swayamraina.flashcard.storage.entity.vo.Month;
import dev.swayamraina.flashcard.storage.entity.vo.Day;
import dev.swayamraina.flashcard.storage.entity.vo.Year;

import java.util.*;

public class Memory {

    private Day today;
    public Day today () { return today; }

    private Month month;
    public Month month () { return month; }

    private Year year;
    public Year year () { return year; }

    private Calendar calendar;
    public Calendar calendar () { return calendar; }
    private void initCalendar () {
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
    }


    public Memory () {
        initCalendar();
        today = new Day();
        month = new Month();
        year  = new Year();
    }

}
