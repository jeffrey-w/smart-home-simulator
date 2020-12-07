package test.main.util;

import main.util.SeasonCheck;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertSame;

class SeasonCheckTest {

    @Test
    void testGetSeason() {
        // test the four season
        Date date = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        assertSame(SeasonCheck.Season.WINTER, SeasonCheck.getSeason(date));

        date = new GregorianCalendar(2020, Calendar.APRIL, 1).getTime();
        assertSame(SeasonCheck.Season.SPRING, SeasonCheck.getSeason(date));

        date = new GregorianCalendar(2020, Calendar.JULY, 1).getTime();
        assertSame(SeasonCheck.Season.SUMMER, SeasonCheck.getSeason(date));

        date = new GregorianCalendar(2020, Calendar.OCTOBER, 1).getTime();
        assertSame(SeasonCheck.Season.FALL, SeasonCheck.getSeason(date));
    }
}