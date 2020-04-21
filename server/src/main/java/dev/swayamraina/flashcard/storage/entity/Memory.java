package dev.swayamraina.flashcard.storage.entity;

import dev.swayamraina.flashcard.storage.entity.vo.Month;
import dev.swayamraina.flashcard.storage.entity.vo.Day;
import dev.swayamraina.flashcard.storage.entity.vo.Year;

import java.util.*;

public class Memory {

    private Day today;
    public Day dayHolder () { return today; }
    public Set<String> today () { return today.get(); }

    private Month month;
    public Month monthHolder () { return month; }
    public CircularBuffer<Day> month () { return month.get(); }

    private Year year;
    public Year yearHolder () { return year; }
    public CircularBuffer<Month> year () { return year.get(); }

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
