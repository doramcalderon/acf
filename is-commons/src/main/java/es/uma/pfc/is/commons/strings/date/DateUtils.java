package es.uma.pfc.is.commons.strings.date;

import java.util.Calendar;

/**
 * Date utils.
 *@author Dora Calderón
 */
public class DateUtils {
    /**
     * Returns a string with current date, with the format yyyymmddMMss
     * @return Date string.
     */
    public static String getCurrentDateString() {
        
        Calendar cal = Calendar.getInstance();
        String currentInstantDir = String.format("%4d", cal.get(Calendar.YEAR))
                + String.format("%02d", cal.get(Calendar.MONTH))
                + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))
                + String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))
                + String.format("%02d", cal.get(Calendar.MINUTE))
                + String.format("%02d", cal.get(Calendar.SECOND));
        return currentInstantDir;
    }
}
