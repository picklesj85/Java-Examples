import java.util.Calendar;

/**
 * Created by James Pickles on 02/03/2016.
 */
public final class DateInstanceImpl implements DateInstance {
    private static boolean dateChanged;
    private static Calendar fakeDate;

    /**
     * @see DateInstance#getDateInstance()
     */
    public Calendar getDateInstance() {
        return (dateChanged) ? fakeDate : Calendar.getInstance();
    }

    public void changeDate(Calendar newDate) {
        fakeDate = newDate;
        dateChanged = true;
    }

    /**
     * @see DateInstance#reset()
     */
    public void reset() {
        dateChanged = false;
    }
}