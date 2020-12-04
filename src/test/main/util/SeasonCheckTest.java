package main.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class SeasonCheckTest {

    @Test
    void testGetSeason() {
        // test the four season
        Date date = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        assertTrue(SeasonCheck.Season.WINTER == SeasonCheck.getSeason(date));

        date = new GregorianCalendar(2020, Calendar.APRIL, 1).getTime();
        assertTrue(SeasonCheck.Season.SPRING == SeasonCheck.getSeason(date));

        date = new GregorianCalendar(2020, Calendar.JULY, 1).getTime();
        assertTrue(SeasonCheck.Season.SUMMER == SeasonCheck.getSeason(date));

        date = new GregorianCalendar(2020, Calendar.OCTOBER, 1).getTime();
        assertTrue(SeasonCheck.Season.FALL == SeasonCheck.getSeason(date));
    }
}