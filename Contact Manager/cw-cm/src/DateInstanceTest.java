import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by James Pickles on 02/03/2016.
 */

public class DateInstanceTest {
    DateInstance di;
    Calendar cal1, cal2;

    @Before
    public void setUp() {
        di = new DateInstanceImpl();
        di.reset();
    }

    @Test
    public void testsGetCurrentTime() {
        cal1 = Calendar.getInstance();
        //assuming there will be a gap so will only check to the second
        assertEquals(cal1.get(Calendar.YEAR), di.getDateInstance().get(Calendar.YEAR));
        assertEquals(cal1.get(Calendar.DAY_OF_YEAR), di.getDateInstance().get(Calendar.DAY_OF_YEAR));
        assertEquals(cal1.get(Calendar.HOUR), di.getDateInstance().get(Calendar.HOUR));
        assertEquals(cal1.get(Calendar.MINUTE), di.getDateInstance().get(Calendar.MINUTE));
        assertEquals(cal1.get(Calendar.SECOND), di.getDateInstance().get(Calendar.SECOND));
    }

    @Test
    public void testChangeDate() {
        cal1 = new GregorianCalendar(2017, 11, 12);
        di.changeDate(cal1);
        cal2 = di.getDateInstance();
        assertEquals(cal1, di.getDateInstance());
        assertEquals(2017, cal2.get(Calendar.YEAR));
        assertEquals(11, cal2.get(Calendar.MONTH));
        assertEquals(12, cal2.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testsCompareDatesWithBeforeAndAfter() {
        cal1 = new GregorianCalendar(2020, 11, 12);
        di.changeDate(cal1);
        assertTrue(di.getDateInstance().after(Calendar.getInstance()));
        assertTrue(di.getDateInstance().before(new GregorianCalendar(2020, 11, 13)));
        assertEquals(di.getDateInstance(), cal1);
        assertTrue(new GregorianCalendar().before(di.getDateInstance()));
    }

    @Test
    public void testsReset() {
        di.changeDate(new GregorianCalendar(2016, 11, 25));
        assertTrue(di.getDateInstance().equals(new GregorianCalendar(2016, 11, 25)));
        di.reset();
        cal1 = di.getDateInstance();
        cal2 = new GregorianCalendar();
        assertEquals(cal1.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        assertEquals(cal1.get(Calendar.DAY_OF_YEAR), cal2.get(Calendar.DAY_OF_YEAR));
        assertEquals(cal1.get(Calendar.HOUR), cal2.get(Calendar.HOUR));
        assertEquals(cal1.get(Calendar.MINUTE), cal2.get(Calendar.MINUTE));
        assertEquals(cal1.get(Calendar.SECOND), cal2.get(Calendar.SECOND));
    }
}