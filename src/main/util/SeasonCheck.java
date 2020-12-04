package main.util;

import java.util.Calendar;
import java.util.Date;

public class SeasonCheck {
    public enum Season {
        SPRING,
        SUMMER,
        FALL,
        WINTER
    };

    private static final Season seasons[] = {
            Season.WINTER, Season.WINTER, Season.SPRING, Season.SPRING, Season.SUMMER, Season.SUMMER,
            Season.SUMMER, Season.SUMMER, Season.FALL, Season.FALL, Season.WINTER, Season.WINTER
    };

    // return the season given the date
    public static Season getSeason(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return seasons[month];
    }

    private SeasonCheck() {
        throw new AssertionError();
    }
}
