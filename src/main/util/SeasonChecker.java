package main.util;

import java.util.Calendar;
import java.util.Date;

public class SeasonChecker {

    public enum Season {

        SPRING {
            @Override
            Date start(Date date) {
                CALENDAR.set(date.getYear(), Calendar.MARCH, 1, 0, 0, 0);
                return CALENDAR.getTime();
            }

            @Override
            Date end(Date date) {
                CALENDAR.set(date.getYear(), Calendar.JUNE, 1, 0, 0, 0);
                return CALENDAR.getTime();
            }
        },
        SUMMER {
            @Override
            Date start(Date date) {
                CALENDAR.set(date.getYear(), Calendar.JUNE, 1, 0, 0, 0);
                return CALENDAR.getTime();
            }

            @Override
            Date end(Date date) {
                CALENDAR.set(date.getYear(), Calendar.SEPTEMBER, 1, 0, 0, 0);
                return CALENDAR.getTime();
            }
        },
        FALL {
            @Override
            Date start(final Date date) {
                CALENDAR.set(date.getYear(), Calendar.SEPTEMBER, 1, 0, 0, 0);
                return CALENDAR.getTime();
            }

            @Override
            Date end(final Date date) {
                CALENDAR.set(date.getYear(), Calendar.DECEMBER, 1, 0, 0, 0);
                return CALENDAR.getTime();
            }
        },
        WINTER {
            @Override
            Date start(final Date date) {
                CALENDAR.set(date.getYear(), Calendar.DECEMBER, 1, 0, 0, 0);
                return CALENDAR.getTime();
            }

            @Override
            Date end(final Date date) {
                CALENDAR.set(date.getYear(), Calendar.MARCH, 1, 0, 0, 0);
                return CALENDAR.getTime();
            }
        };

        abstract Date start(Date date);
        abstract Date end(Date date);

    }

    private static final Calendar CALENDAR = Calendar.getInstance();

    public static boolean isIn(Date date, Season season) {
        return date.compareTo(season.start(date)) >= 0 && date.compareTo(season.end(date)) < 0;
    }

    private SeasonChecker() {
        throw new AssertionError();
    }

}
