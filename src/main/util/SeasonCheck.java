package main.util;

import java.util.Calendar;
import java.util.Date;

/**
 * The {@code SeasonCheck} class validates which season the user-chosen date falls in.
 * The seasons are as follows:
 *     - Spring (March to April)
 *     - Summer (May to August)
 *     - Fall (September to October)
 *     - Winter (November to February)
 *
 * @author Philippe Vo
 */
public class SeasonCheck {

    public enum Season {
        SPRING,
        SUMMER,
        FALL,
        WINTER
    }

    // Each element of this array represents a month of the year and the season it corresponds to
    private static final Season[] seasons = {
            Season.WINTER, Season.WINTER, Season.SPRING, Season.SPRING, Season.SUMMER, Season.SUMMER,
            Season.SUMMER, Season.SUMMER, Season.FALL, Season.FALL, Season.WINTER, Season.WINTER
    };

    /**
     * @param date The date to validate
     * @return The season the date falls in
     */
    public static Season getSeason(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        return seasons[month];
    }

    /**
     * @param date The date to validate
     * @return {@code true} if the date is in Spring
     */
    public static boolean isSpring(Date date) {
        return getSeason(date) == Season.SPRING;
    }

    /**
     * @param date The date to validate
     * @return {@code true} if the date is in Summer
     */
    public static boolean isSummer(Date date) {
        return getSeason(date) == Season.SUMMER;
    }

    /**
     * @param date The date to validate
     * @return {@code true} if the date is in Fall
     */
    public static boolean isFall(Date date) {
        return getSeason(date) == Season.FALL;
    }

    /**
     * @param date The date to validate
     * @return {@code true} if the date is in Winter
     */
    public static boolean isWinter(Date date) {
        return getSeason(date) == Season.WINTER;
    }

    private SeasonCheck() {
        throw new AssertionError();
    }
}
