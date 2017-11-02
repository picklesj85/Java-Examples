import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by James Pickles on 22/02/2016.
 */
public class FutureMeetingImplTest {
    Set<Contact> meetingContacts;
    Meeting testMeeting;
    Contact jim, ben;
    Calendar testDate;

    @Before
    public void setUp() {
        jim = new ContactImpl(1, "Jim", "test");
        ben = new ContactImpl(2, "Ben", "test2");
        meetingContacts = new HashSet<>();
        meetingContacts.add(jim);
        meetingContacts.add(ben);
        testDate = new GregorianCalendar(2015, 2, 10);
        testMeeting = new FutureMeetingImpl(5, testDate, meetingContacts);
    }

    @Test(expected = NullPointerException.class)
    public void testsNullParameter() {
        new FutureMeetingImpl(3, null, meetingContacts);
    }

    @Test(expected = NullPointerException.class)
    public void testsDifferentNullParameter() {
        Calendar date = new GregorianCalendar(2015, 1, 22);
        new FutureMeetingImpl(3, date, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingZeroAsId() {
        Calendar date = new GregorianCalendar(2015, 5, 2);
        new FutureMeetingImpl(0, date, meetingContacts);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingNegativeAsId() {
        Calendar date = new GregorianCalendar(2015, 4, 20);
        new FutureMeetingImpl(-5, date, meetingContacts);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingEmptySet() {
        Calendar date = new GregorianCalendar(2016, 11, 11);
        Set<Contact> emptyTest = new HashSet<>();
        new FutureMeetingImpl(4, date, emptyTest);
    }

    @Test
    public void testsGetIdOfMeeting() {
        assertEquals(5, testMeeting.getId());
    }

    @Test
    public void testsWrongId() {
        assertFalse(1 == testMeeting.getId());
    }

    @Test
    public void testGetDate() {
        assertEquals(new GregorianCalendar(2015, 2, 10), testMeeting.getDate());
    }

    @Test
    public void testsWrongDate() {
        assertFalse(new GregorianCalendar(2016, 2, 10).equals(testMeeting.getDate()));
    }

    @Test
    public void testGetContacts() {
        assertEquals(meetingContacts, testMeeting.getContacts());
    }

    @Test
    public void testsWrongSetOfContacts() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        assertFalse(testSet.equals(testMeeting.getContacts()));
    }

    @Test
    public void testsTwoMeetingsTheSame() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        testSet.add(jim);
        Calendar testDate2 = new GregorianCalendar(2015, 2, 10);
        Meeting testMeet = new FutureMeetingImpl(5, testDate2, testSet);
        assertTrue(testMeet.equals(testMeeting));
    }

    @Test
    public void testsDifferentIds() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        testSet.add(jim);
        Calendar testDate2 = new GregorianCalendar(2015, 2, 10);
        Meeting testMeeting2 = new FutureMeetingImpl(4, testDate2, testSet);
        assertFalse(testMeeting2.equals(testMeeting));
    }

    @Test
    public void testsDifferentDates() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        testSet.add(jim);
        Calendar testDate2 = new GregorianCalendar(2015, 2, 11);
        Meeting testMeeting2 = new FutureMeetingImpl(5, testDate2, testSet);
        assertFalse(testMeeting2.getDate().equals(testMeeting.getDate()));
    }

    @Test
    public void testsDifferentContacts() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(jim);
        Calendar testDate2 = new GregorianCalendar(2015, 2, 10);
        Meeting testMeeting2 = new FutureMeetingImpl(5, testDate2, testSet);
        assertFalse(testMeeting2.getContacts().equals(testMeeting.getContacts()));
    }
}