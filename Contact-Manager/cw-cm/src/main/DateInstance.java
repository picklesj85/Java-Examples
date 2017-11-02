/**
 * Created by JAmes Pickles on 02/03/2016.
 */

import java.util.Calendar;

/**
 * This is a helper class to allow manipulation of the current time/date for testing purposes.
 *
 */
public interface DateInstance {

    /**
     * This method should be called by ContactManager whenever it needs to
     * get the current time/date.
     *
     * @return a Calendar object of the current date/time.
     */
    Calendar getDateInstance();

    /**
     * This method can be used to change the current date
     * to a "fake" date for testing purposes.
     *
     * @param newDate the new "fake" date.
     */
    void changeDate(Calendar newDate);

    /**
     * This method is used to reset the current date to the real time.
     */
    void reset();
}
