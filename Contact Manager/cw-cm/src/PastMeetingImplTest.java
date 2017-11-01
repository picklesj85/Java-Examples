import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by James Pickles on 23/02/2016.
 */
public class PastMeetingImplTest {
    Set<Contact> meetingContacts;
    PastMeeting testMeeting;
    Contact jim, ben;
    Calendar testDate;

    @Before
    public void setUp() {
        meetingContacts = new HashSet<>();
        jim = new ContactImpl(1, "Jim", "test");
        ben = new ContactImpl(2, "Ben", "test2");
        meetingContacts.add(jim);
        meetingContacts.add(ben);
        testDate = new GregorianCalendar(2014, 3, 11);
        testMeeting = new PastMeetingImpl(2, testDate, meetingContacts, "testNotes");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddingNullDate() {
        new PastMeetingImpl(2, null, meetingContacts, "testNotes");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddingNullContacts() {
        new PastMeetingImpl(2, testDate, null, "testNotes");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddingNullNotes() {
        new PastMeetingImpl(2, testDate, meetingContacts, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingZeroID() {
        new PastMeetingImpl(0, testDate, meetingContacts, "testNotes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingNegativeID() {
        new PastMeetingImpl(-3, testDate, meetingContacts, "testNotes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingEmptyContactSet() {
        Set<Contact> emptySet = new HashSet<>();
        new PastMeetingImpl(1, testDate, emptySet, "testNotes");
    }

    @Test
    public void testsGetMeetingId() {
        assertEquals(2, testMeeting.getId());
    }

    @Test
    public void testsWrongId() {
        assertFalse(1 == testMeeting.getId());
    }

    @Test
    public void testGetDate() {
        assertEquals(new GregorianCalendar(2014, 3, 11), testMeeting.getDate());
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
    public void testsGetNotes() {
        assertEquals("testNotes", testMeeting.getNotes());
    }

    @Test
    public void testsWrongNotes() {
        assertFalse("testNote".equals(testMeeting.getNotes()));
    }

    @Test
    public void testsTwoMeetingSame() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        testSet.add(jim);
        Calendar testDate2 = new GregorianCalendar(2014, 3, 11);
        String notes = "testNotes";
        PastMeeting meet2 = new PastMeetingImpl(2, testDate2, testSet, notes);
        assertTrue(meet2.equals(testMeeting));
    }

    @Test
    public void testsDifferentNotes() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        testSet.add(jim);
        Calendar testDate2 = new GregorianCalendar(2014, 3, 11);
        String notes = "testNote";
        PastMeeting meet2 = new PastMeetingImpl(2, testDate2, testSet, notes);
        assertFalse(meet2.getNotes().equals(testMeeting.getNotes()));
    }

    @Test
    public void testsDifferentId() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        testSet.add(jim);
        Calendar testDate2 = new GregorianCalendar(2014, 3, 11);
        String notes = "testNotes";
        PastMeeting meet2 = new PastMeetingImpl(1, testDate2, testSet, notes);
        assertFalse(meet2.equals(testMeeting));
    }

    @Test
    public void testsDifferentDate() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        testSet.add(jim);
        Calendar testDate2 = new GregorianCalendar(2014, 3, 12);
        String notes = "testNotes";
        PastMeeting meet2 = new PastMeetingImpl(2, testDate2, testSet, notes);
        assertFalse(meet2.getDate().equals(testMeeting.getDate()));
    }

    @Test
    public void testsDifferentContactSet() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(ben);
        Calendar testDate2 = new GregorianCalendar(2014, 3, 11);
        String notes = "testNotes";
        PastMeeting meet2 = new PastMeetingImpl(2, testDate2, testSet, notes);
        assertFalse(meet2.getContacts().equals(testMeeting.getContacts()));
    }
}
