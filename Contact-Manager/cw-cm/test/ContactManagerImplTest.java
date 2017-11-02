import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by James Pickles on 23/02/2016.
 */
public class ContactManagerImplTest {
    ContactManager cm, cm1, cm2, cm3;
    Set<Contact> testSet1, testSet2, testSet3;
    Contact con1, con2, con3;
    Calendar current, plus1Sec, plus1Min, plus1Hour, plus1Day, plus1Month, plus1Year,
             minus1Min, minus1Sec, minus1Hour, minus1Day, minus1Month, minus1Year;
    DateInstance di = new DateInstanceImpl();
    final File contacts = new File("contacts.txt");

    @Before
    public void setUp() {

        //clear existing data from file
        if (contacts.exists()) {
            contacts.delete();
        }

        //reset DateInstance so it returns current date
        di.reset();

        //create 3 test contacts
        con1 = new ContactImpl(1, "Con1", "test contact 1");
        con2 = new ContactImpl(2, "Con2", "test contact 2");
        con3 = new ContactImpl(3, "Con3", "test contact 3");

        //create 3 test contact sets
        //one test set with one contact
        testSet1 = new HashSet<>();
        testSet1.add(con1);
        //one test set with two contacts
        testSet2 = new HashSet<>();
        testSet2.add(con1);
        testSet2.add(con2);
        //one test set with three contacts
        testSet3 = new HashSet<>();
        testSet3.add(con1);
        testSet3.add(con2);
        testSet3.add(con3);

        //create four test ContactManagers
        //one with no contacts
        cm = new ContactManagerImpl();
        //one with one contact
        cm1 = new ContactManagerImpl();
        cm1.addNewContact("Con1", "test contact 1");
        //one with two contacts
        cm2 = new ContactManagerImpl();
        cm2.addNewContact("Con1", "test contact 1");
        cm2.addNewContact("Con2", "test contact 2");
        //one with three contacts
        cm3 = new ContactManagerImpl();
        cm3.addNewContact("Con1", "test contact 1");
        cm3.addNewContact("Con2", "test contact 2");
        cm3.addNewContact("Con3", "test contact 3");

        //create a time in sync with system time to use during testing
        //so that tests will also work in the future
        current = new GregorianCalendar();
        plus1Sec = new GregorianCalendar();
        minus1Sec = new GregorianCalendar();
        plus1Min = new GregorianCalendar();
        plus1Hour = new GregorianCalendar();
        plus1Day = new GregorianCalendar();
        plus1Month = new GregorianCalendar();
        plus1Year = new GregorianCalendar();
        minus1Min = new GregorianCalendar();
        minus1Hour = new GregorianCalendar();
        minus1Day = new GregorianCalendar();
        minus1Month = new GregorianCalendar();
        minus1Year = new GregorianCalendar();
        plus1Sec.add(Calendar.SECOND, 1);
        plus1Min.add(Calendar.MINUTE, 1);
        plus1Hour.add(Calendar.HOUR, 1);
        plus1Day.add(Calendar.DATE, 1);
        plus1Month.add(Calendar.MONTH, 1);
        plus1Year.add(Calendar.YEAR, 1);
        minus1Sec.add(Calendar.SECOND, -1);
        minus1Min.add(Calendar.MINUTE, -1);
        minus1Hour.add(Calendar.HOUR, -1);
        minus1Day.add(Calendar.DATE, -1);
        minus1Month.add(Calendar.MONTH, -1);
        minus1Year.add(Calendar.YEAR, -1);
    }

    /**
     * Test addNewContact()
     */

    @Test(expected = NullPointerException.class)
    public void testsAddNullName() {
        cm.addNewContact(null, "test");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddNullNotes() {
        cm.addNewContact("Bob Smith", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddEmptyName() {
        cm.addNewContact("", "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddEmptyNotes() {
        cm.addNewContact("Bob", "");
    }

    @Test
    public void testsAddOneNewContact() {
        assertEquals(1, cm.addNewContact("Bob", "First Contact"));
    }

    @Test
    public void testsAddTwoNewContacts() {
        cm.addNewContact("Tim", "First Contact");
        assertEquals(2, cm.addNewContact("Bob", "Second Contact"));
    }

    @Test
    public void testsAddThreeNewContacts() {
        cm.addNewContact("Tim", "First Contact");
        cm.addNewContact("Jo", "Second Contact");
        assertEquals(3, cm.addNewContact("Bob", "Third Contact"));
    }

    @Test
    public void testsAddOneHundredContacts() {
        for (int i = 0; i < 99; i++) {
            cm.addNewContact("Contact " + i, "test");
        }
        assertEquals(100, cm.addNewContact("Dan", "test"));
    }

    /**
     * Test getContact(int)
     */

    @Test(expected = IllegalArgumentException.class)
    public void testsNoIdsProvided() {
        cm.addNewContact("Ben", "notes");
        cm.getContacts();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsIdProvidedDoesNotExist() {
        cm.addNewContact("Ben", "notes");
        cm.getContacts(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsTwoValidIdsButOneInvalid() {
        cm.addNewContact("Ben", "notes");
        cm.addNewContact("Tim", "notes1");
        cm.getContacts(1, 2, 3);
    }

    @Test
    public void testsGetOneContact() {
        cm.addNewContact("Ben", "myNotes");
        Set<Contact> testSet = cm.getContacts(1);
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm.getContacts(1)));
    }

    @Test
    public void testsGetOneContactComparesName() {
        cm.addNewContact("Ben", "myNotes");
        Set<Contact> testSet = cm.getContacts(1);
        Object[] ob = testSet.toArray();
        Contact c = (Contact) ob[0];
        assertEquals("Ben", c.getName());
    }

    @Test
    public void testsGetTwo() {
        Set<Contact> testSet = cm2.getContacts(1, 2);
        assertEquals(2, testSet.size());
        assertTrue(testSet.containsAll(cm2.getContacts(1, 2)));
        for (Contact c : testSet) {
            assertTrue(c.equals(con1) || c.equals(con2));
        }
    }

    @Test
    public void testsGetTwoOutOfThree() {
        Set<Contact> testSet = cm3.getContacts(1, 3);
        assertEquals(2, cm3.getContacts(1, 3).size());
        assertEquals(2, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts(3, 1)));
        for (Contact c : testSet) {
            assertTrue(c.equals(con1) || c.equals(con3));
        }
    }

    @Test
    public void testsGetThreeContacts() {
        cm.addNewContact("Ben", "myNotes");
        cm.addNewContact("Tim", "myNotes");
        cm.addNewContact("Ell", "myNotes");
        Set<Contact> testSet = cm.getContacts(1, 2, 3);
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm.getContacts(1, 2, 3)));
    }

    @Test
    public void testsGetThreeContactsDifferentOrder() {
        Set<Contact> testSet = cm3.getContacts(2, 3, 1);
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts(1)));
        assertTrue(testSet.containsAll(cm3.getContacts(2)));
        assertTrue(testSet.containsAll(cm3.getContacts(3)));
        for (Contact c : testSet) {
            assertTrue(c.equals(con1) || c.equals(con2) || c.equals(con3));
        }
    }

    @Test
    public void testsAddOneFromGroupOfThree() {
        Set<Contact> testSet = cm3.getContacts(3);
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts(3)));
        assertFalse(testSet.containsAll(cm3.getContacts(1)));
        assertFalse(testSet.containsAll(cm3.getContacts(2)));
        assertFalse(testSet.containsAll(cm3.getContacts(1, 2, 3)));
        for (Contact c : testSet) {
            assertTrue(c.equals(con3));
        }
    }

    /**
     * Test getContact(String)
     */


    @Test(expected = NullPointerException.class)
    public void testsNullAsArgument() {
        String strNull = null;
        cm1.getContacts(strNull);
    }

    @Test
    public void testGetOneContact() {
        Set<Contact> testSet = cm3.getContacts("Con3");
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("Con3")));
        for (Contact c : testSet) {
            assertEquals("Con3", c.getName());
            assertEquals(c, con3);
        }
    }

    @Test
    public void testsGetOneContactLowerCase() {
        Set<Contact> testSet = cm2.getContacts("con2");
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm2.getContacts("con2")));
        for (Contact c : testSet) {
            assertEquals("Con2", c.getName());
            assertEquals(c, con2);
        }
    }

    @Test
    public void testsGetAllContacts() {
        Set<Contact> testSet = cm3.getContacts("");
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("")));
        for (Contact c : testSet) {
            assertTrue(c.getId() == 1 || c.getId() == 2 || c.getId() == 3);
            assertTrue(c.equals(con1) || c.equals(con2) || c.equals(con3));
        }
    }

    @Test
    public void testsPartialName() {
        Set<Contact> testSet = cm3.getContacts("con");
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("con")));
        for (Contact c : testSet) {
            assertTrue(c.getNotes().equals("test contact 1") || c.getNotes().equals("test contact 2") || c.getNotes().equals("test contact 3"));
            assertTrue(c.equals(con1) || c.equals(con2) || c.equals(con3));
        }
    }

    @Test
    public void testsFirstLetterOnly() {
        Set<Contact> testSet = cm3.getContacts("c");
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("c")));
        for (Contact c : testSet) {
            assertTrue(c.equals(con1) || c.equals(con2) || c.equals(con3));
        }
    }

    //for now assuming that we return an empty list if no matches are found
    @Test
    public void testsNameNotInContacts() {
        Set<Contact> testSet = cm3.getContacts("fake contact");
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void testsGettingContactWithSpaces() {
        cm3.addNewContact("Joe Bloggs", "test");
        Set<Contact> testSet = cm3.getContacts("joe b");
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("joe b")));
        for (Contact c : testSet) {
            assertEquals("Joe Bloggs", c.getName());
            assertEquals(4, c.getId());
        }
    }

    //assuming that when there are no contacts an empty list is returned
    @Test
    public void testsSearchingForContactButNoContacts() {
        Set<Contact> testSet = cm.getContacts("Mike");
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void testsSearchForAllContactsButNoContacts() {
        Set<Contact> testSet = cm.getContacts("");
        assertTrue(testSet.isEmpty());
    }

    /**
     * Test addFutureMeeting()
     */

    @Test(expected = NullPointerException.class)
    public void testsNullDatePassed() {
        cm1.addFutureMeeting(testSet1,null);
    }

    @Test(expected = NullPointerException.class)
    public void testsNullContactSetPassed() {
        cm1.addFutureMeeting(null, plus1Day);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Second() {
        cm1.addFutureMeeting(testSet1, minus1Sec);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Minute() {
        cm1.addFutureMeeting(testSet1, minus1Min);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Hour() {
        cm1.addFutureMeeting(testSet1, minus1Hour);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Day() {
        cm1.addFutureMeeting(testSet1, minus1Day);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Month() {
        cm1.addFutureMeeting(testSet1, minus1Month);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Year() {
        cm1.addFutureMeeting(testSet1, minus1Year);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsEmptySetOfContacts() {
        Set<Contact> empty = new HashSet<>();
        cm3.addFutureMeeting(empty, plus1Month);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsUnknownContact() {
        Set<Contact> testSet = new HashSet<>();
        testSet.add(new ContactImpl(1, "Test", "notes"));
        cm.addFutureMeeting(testSet, plus1Year);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsSetWithUnknownContact() {
        cm2.addFutureMeeting(testSet3, plus1Year);
    }


    @Test
    public void testsCorrectIdReturned() {
        int result = cm1.addFutureMeeting(testSet1, plus1Year);
        assertEquals(1, result);
    }

    @Test
    public void testsNextIdCorrect() {
        cm1.addFutureMeeting(testSet1, plus1Year);
        int result = cm1.addFutureMeeting(testSet1, plus1Month);
        assertEquals(2, result);
    }

    @Test
    public void testsThirdIdCorrect() {
        cm1.addFutureMeeting(testSet1, plus1Year);
        cm1.addFutureMeeting(testSet1, plus1Month);
        int result = cm1.addFutureMeeting(testSet1, plus1Day);
        assertEquals(3, result);
    }

    @Test
    public void testsOneSecondAhead() {
        int result = cm1.addFutureMeeting(testSet1, plus1Sec);
        assertEquals(1, result);
    }

    @Test
    public void tests1HourAhead() {
        int result = cm1.addFutureMeeting(testSet1, plus1Hour);
        assertEquals(1, result);
    }

    @Test
    public void tests1MinuteAhead() {
        int result = cm1.addFutureMeeting(testSet1, plus1Min);
        assertEquals(1, result);
    }

    @Test
    public void tests2ContactSet() {
        int result = cm2.addFutureMeeting(testSet2, plus1Month);
        assertEquals(1, result);
    }

    @Test
    public void tests3ContactSet() {
        int result = cm3.addFutureMeeting(testSet3, plus1Day);
        assertEquals(1, result);
    }


    /**
     * test addNewPastMeeting()
     */

    @Test(expected = NullPointerException.class)
    public void testsAddingNullContacts() {
        cm1.addNewPastMeeting(null, minus1Year, "test meeting");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddingNullDate() {
        cm1.addNewPastMeeting(testSet1, null, "test meeting");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddingNullNotes() {
        cm1.addNewPastMeeting(testSet1, minus1Year, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsEmptyContactSet() {
        Set<Contact> testSet = new HashSet<>();
        cm1.addNewPastMeeting(testSet, minus1Month, "Test Notes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsNotKnownContact() {
        cm1.addNewPastMeeting(testSet2, minus1Day, "notes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingMeetingWhenNoContactsExist() {
        cm.addNewPastMeeting(testSet1, minus1Hour, "notes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsFutureDateOneSec() {
        cm1.addNewPastMeeting(testSet1, plus1Sec, "notes");
    }

    @Test
    public void testsAddOnePastMeeting() {
        assertNull(cm1.getPastMeeting(1));
        cm1.addNewPastMeeting(testSet1, minus1Day, "past meeting");
        assertNotNull(cm1.getPastMeeting(1));
    }

    @Test
    public void testsAddTwoPastMeetings() {
        assertNull(cm2.getPastMeeting(1));
        assertNull(cm2.getPastMeeting(2));
        cm2.addNewPastMeeting(testSet1, minus1Year, "test");
        assertNotNull(cm2.getPastMeeting(1));
        assertNull(cm2.getPastMeeting(2));
        cm2.addNewPastMeeting(testSet2, minus1Month, "second meeting");
        assertNotNull(cm2.getPastMeeting(2));
    }

    @Test
    public void testsAddFutureMeetingThenPastMeeting() {
        cm3.addFutureMeeting(testSet1, plus1Day);
        assertNull(cm3.getPastMeeting(2));
        cm3.addNewPastMeeting(testSet3, minus1Hour, "meeting");
        assertNotNull(cm3.getPastMeeting(2));
    }

    @Test
    public void testsAddMultiplePastAndFutureMeetings() {
        cm3.addFutureMeeting(testSet1, plus1Day);
        assertNull(cm3.getPastMeeting(2));
        assertNull(cm3.getPastMeeting(3));
        cm3.addNewPastMeeting(testSet2, minus1Min, "test");
        cm3.addNewPastMeeting(testSet3, minus1Year,"tet2");
        assertNotNull(cm3.getPastMeeting(2));
        assertNotNull(cm3.getPastMeeting(3));
        cm3.addFutureMeeting(testSet1, plus1Year);
        assertNull(cm3.getPastMeeting(5));
        cm3.addNewPastMeeting(testSet1, minus1Sec, "test3");
        assertNotNull(cm3.getPastMeeting(5));
    }

    @Test
    public void addPastMeetingWithEmptyNotes() {
        cm.addNewContact("Con1", "test contact 1");
        assertNull(cm.getPastMeeting(1));
        cm.addNewPastMeeting(testSet1, minus1Min, "meeting");
        assertNotNull(cm.getPastMeeting(1));
    }

    /**
     * test getPastMeeting()
     */

    @Test(expected = IllegalStateException.class)
    public void testsIdForFutureMeetingNotPast() {
        cm1.addFutureMeeting(testSet1, plus1Day);
        cm1.getPastMeeting(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testsAddTwoMeetingsTryAndFindPastWithFutureId() {
        cm1.addNewPastMeeting(testSet1, minus1Min, "test");
        cm1.addFutureMeeting(testSet1, plus1Day);
        cm1.getPastMeeting(2);
    }

    @Test
    public void testsGetPastMeetingNoPastMeetings() {
        assertNull(cm1.getPastMeeting(1));
    }

    @Test
    public void testsGetPastMeetingWrongId() {
        cm2.addNewPastMeeting(testSet1, minus1Sec, "notes1");
        cm2.addNewPastMeeting(testSet2, minus1Month, "notes2");
        assertNull(cm2.getPastMeeting(3));
    }

    @Test
    public void testsGetNonExistentMeetingAfterAddingFutureMeeting() {
        assertNull(cm1.getPastMeeting(1));
        cm1.addFutureMeeting(testSet1, plus1Day);
        assertNull(cm1.getPastMeeting(2));
    }

    @Test
    public void testsGetDetailsOfOneMeetingAdded() {
        cm2.addNewPastMeeting(testSet2, minus1Year, "my test notes");
        assertEquals("my test notes", cm2.getPastMeeting(1).getNotes());
        assertEquals(testSet2, cm2.getPastMeeting(1).getContacts());
        assertEquals(minus1Year, cm2.getPastMeeting(1).getDate());
    }

    @Test
    public void testsGetDetailsOfSecondAddedOfMultiplePastMeetings() {
        cm3.addNewPastMeeting(testSet1, minus1Sec, "first");
        cm3.addNewPastMeeting(testSet2, minus1Day, "second");
        cm3.addNewPastMeeting(testSet3, minus1Hour, "third");
        assertEquals(2, cm3.getPastMeeting(2).getId());
        assertEquals(testSet2, cm3.getPastMeeting(2).getContacts());
        assertEquals("second", cm3.getPastMeeting(2).getNotes());
        assertEquals(minus1Day, cm3.getPastMeeting(2).getDate());
    }

    @Test
    public void testsGetDetailsOfThirdAddedOfMultiplePastMeetings() {
        cm3.addNewPastMeeting(testSet1, minus1Sec, "first");
        cm3.addNewPastMeeting(testSet2, minus1Day, "second");
        cm3.addNewPastMeeting(testSet3, minus1Hour, "third");
        assertEquals(3, cm3.getPastMeeting(3).getId());
        assertEquals(testSet3, cm3.getPastMeeting(3).getContacts());
        assertEquals("third", cm3.getPastMeeting(3).getNotes());
        assertEquals(minus1Hour, cm3.getPastMeeting(3).getDate());
    }

    @Test
    public void testsMeetingReturnedIsSameAsAdded() {
        cm2.addNewPastMeeting(testSet1, minus1Month, "testMeet");
        PastMeeting testMeet = cm2.getPastMeeting(1);
        assertEquals(testSet1, testMeet.getContacts());
        assertEquals(minus1Month, testMeet.getDate());
        assertEquals("testMeet", testMeet.getNotes());
    }

    @Test
    public void testsGetPastMeetingZeroId() {
        cm3.addNewPastMeeting(testSet1, minus1Sec, "first");
        cm3.addNewPastMeeting(testSet2, minus1Day, "second");
        cm3.addNewPastMeeting(testSet3, minus1Hour, "third");
        assertNull(cm3.getPastMeeting(0));
    }

    @Test
    public void testsGetPastMeetingNegativeId() {
        cm3.addNewPastMeeting(testSet1, minus1Sec, "first");
        cm3.addNewPastMeeting(testSet2, minus1Day, "second");
        cm3.addNewPastMeeting(testSet3, minus1Hour, "third");
        assertNull(cm3.getPastMeeting(-3));
    }

    @Test
    public void testsGetAFutureMeetingThatIsNowAPastMeeting() {
        cm3.addFutureMeeting(testSet3, plus1Day);
        cm3.addFutureMeeting(testSet2, plus1Month);
        di.changeDate(plus1Year);
        assertEquals(plus1Day, cm3.getPastMeeting(1).getDate());
        assertEquals(plus1Month, cm3.getPastMeeting(2).getDate());
    }


    /**
     * Test getFutureMeeting()
     */

    @Test(expected = IllegalArgumentException.class)
    public void testsGetAPastMeetingId() {
        cm1.addNewPastMeeting(testSet1, minus1Sec, "past meet");
        cm1.getFutureMeeting(1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testsAddingPastAndFutureAndUsingWrongId() {
        cm3.addFutureMeeting(testSet1, plus1Day);
        cm3.addNewPastMeeting(testSet3, minus1Month, "test");
        cm3.getFutureMeeting(2);
    }

    @Test
    public void testsGetFutureMeetingNoFutureMeetings() {
        assertNull(cm1.getFutureMeeting(1));
    }

    @Test
    public void testsGetFutureMeetingWrongId() {
        cm2.addFutureMeeting(testSet1, plus1Day);
        cm2.addFutureMeeting(testSet2, plus1Year);
        assertNull(cm2.getPastMeeting(3));
    }

    @Test
    public void testsGetNonExistentMeetingAfterAddingPastMeeting() {
        assertNull(cm1.getFutureMeeting(1));
        cm1.addNewPastMeeting(testSet1, minus1Day, "my notes");
        assertNull(cm1.getFutureMeeting(2));
    }

    @Test
    public void testsGetDetailsOfOneFutureMeetingAdded() {
        cm2.addFutureMeeting(testSet2, plus1Sec);
        assertEquals(testSet2, cm2.getFutureMeeting(1).getContacts());
        assertEquals(plus1Sec, cm2.getFutureMeeting(1).getDate());
    }

    @Test
    public void testsGetDetailsOfSecondAddedOfMultipleFutureMeetings() {
        cm3.addFutureMeeting(testSet1, plus1Year);
        cm3.addFutureMeeting(testSet2, plus1Month);
        cm3.addFutureMeeting(testSet3, plus1Day);
        assertEquals(2, cm3.getFutureMeeting(2).getId());
        assertEquals(testSet2, cm3.getFutureMeeting(2).getContacts());
        assertEquals(plus1Month, cm3.getFutureMeeting(2).getDate());
    }

    @Test
    public void testsGetDetailsOfThirdAddedOfMultipleFutureMeetings() {
        cm3.addFutureMeeting(testSet1, plus1Hour);
        cm3.addFutureMeeting(testSet2, plus1Min);
        cm3.addFutureMeeting(testSet3, plus1Day);
        assertEquals(3, cm3.getFutureMeeting(3).getId());
        assertEquals(testSet3, cm3.getFutureMeeting(3).getContacts());
        assertEquals(plus1Day, cm3.getFutureMeeting(3).getDate());
    }

    @Test
    public void testsFutureMeetingReturnedIsSameAsAdded() {
        cm2.addFutureMeeting(testSet2, plus1Hour);
        FutureMeeting testMeet = cm2.getFutureMeeting(1);
        assertEquals(testSet2, testMeet.getContacts());
        assertEquals(plus1Hour, testMeet.getDate());
        assertEquals(1,testMeet.getId());
    }

    @Test
    public void testsGetFutureMeetingZeroId() {
        cm3.addFutureMeeting(testSet1, plus1Month);
        cm3.addFutureMeeting(testSet2, plus1Min);
        cm3.addFutureMeeting(testSet3, plus1Sec);
        assertNull(cm3.getFutureMeeting(0));
    }

    @Test
    public void testsGetFutureMeetingNegativeId() {
        cm1.addFutureMeeting(testSet1, plus1Year);
        assertNull(cm1.getFutureMeeting(-2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsTryingToRetrieveAFutureMeetingThatHasNowHappened() {
        cm1.addFutureMeeting(testSet1, plus1Day);
        assertNotNull(cm1.getFutureMeeting(1));
        di.changeDate(plus1Month);
        cm1.getFutureMeeting(1);
    }

    @Test
    public void testsGetFutureMeetingZeroIdWhenNoFutureMeetings() {
        assertNull(cm1.getFutureMeeting(0));
    }

    /**
     * Test getMeeting()
     */

    @Test
    public void testsGetMeetingNoMeetings() {
        assertNull(cm1.getMeeting(1));
    }

    @Test
    public void testsGetMeetingIdZero() {
        cm2.addFutureMeeting(testSet2, plus1Day);
        assertNull(cm2.getMeeting(0));
    }

    @Test
    public void testsGetMeetingIdNegative() {
        cm1.addNewPastMeeting(testSet1, minus1Month, "");
        cm1.addFutureMeeting(testSet1, plus1Day);
        assertNull(cm1.getMeeting(-1));
    }

    @Test
    public void testsIdNotExist() {
        cm3.addFutureMeeting(testSet1, plus1Year);
        cm3.addNewPastMeeting(testSet3, minus1Month, "notessss");
        assertNull(cm3.getMeeting(3));
    }

    @Test
    public void testsGetMeetingOneFutureMeeting() {
        cm1.addFutureMeeting(testSet1, plus1Year);
        Meeting testMeet = cm1.getMeeting(1);
        assertEquals(1, testMeet.getId());
        assertEquals(testSet1, testMeet.getContacts());
        assertEquals(plus1Year, testMeet.getDate());
    }

    @Test
    public void testsGetMeetingOnePastMeeting() {
        cm2.addNewPastMeeting(testSet2, minus1Month, "testnotes");
        Meeting testMeet = cm2.getMeeting(1);
        assertEquals(1, testMeet.getId());
        assertEquals(testSet2, testMeet.getContacts());
        assertEquals(minus1Month, testMeet.getDate());
        assertEquals("testnotes", ((PastMeeting)testMeet).getNotes());
    }

    @Test
    public void testsAddOneOfEachTypeOfMeetingAndGetDetails() {
        cm2.addFutureMeeting(testSet2, plus1Year);
        cm2.addNewPastMeeting(testSet1, minus1Month, "my notes");
        Meeting testMeet = cm2.getMeeting(1);
        Meeting testMeet2 = cm2.getMeeting(2);
        assertEquals(testSet2, testMeet.getContacts());
        assertEquals(plus1Year, testMeet.getDate());
        assertEquals("my notes", ((PastMeeting)testMeet2).getNotes());
        assertEquals(2, testMeet2.getId());
        assertEquals(minus1Month, testMeet2.getDate());
    }

    @Test
    public void testsAddMultipleOfEachTypeOfMeetingAndGetThem() {
        cm3.addNewPastMeeting(testSet1, minus1Hour, "1");
        cm3.addNewPastMeeting(testSet3, minus1Month, "2");
        cm3.addFutureMeeting(testSet2, plus1Year);
        cm3.addNewPastMeeting(testSet2, minus1Sec, "3");
        cm3.addFutureMeeting(testSet1, plus1Day);
        cm3.addFutureMeeting(testSet2, plus1Hour);
        assertNull(cm3.getMeeting(7));
        assertNotNull(cm3.getMeeting(6));
        assertEquals(minus1Hour, cm3.getMeeting(1).getDate());
        assertEquals(minus1Month, cm3.getMeeting(2).getDate());
        assertEquals(plus1Year, cm3.getMeeting(3).getDate());
        assertEquals(minus1Sec, cm3.getMeeting(4).getDate());
        assertEquals(plus1Day, cm3.getMeeting(5).getDate());
        assertEquals(plus1Hour, cm3.getMeeting(6).getDate());
    }


    /**
     * Test getFutureMeetingList(Contact)
     */

    @Test(expected = NullPointerException.class)
    public void testNullContact() {
        cm1.addFutureMeeting(testSet1, plus1Day);
        cm1.getFutureMeetingList(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsContactDoesNotExist() {
        cm2.addFutureMeeting(testSet2, plus1Year);
        cm2.getFutureMeetingList(con3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsNoContacts() {
        cm.getFutureMeetingList(con1);
    }

    @Test
    public void testsNoFutureMeetings() {
        List<Meeting> testList = cm1.getFutureMeetingList(con1);
        assertTrue(testList.isEmpty());
    }

    @Test
    public void testsOnlyPastMeetingsAdded() {
        cm3.addNewPastMeeting(testSet2, minus1Month, "test1");
        cm3.addNewPastMeeting(testSet3, minus1Hour, "test2");
        assertTrue(cm3.getFutureMeetingList(con2).isEmpty());
    }

    @Test
    public void testsNoFutureMeetingsForSpecifiedContact() {
        cm3.addFutureMeeting(testSet2, plus1Day);
        cm3.addFutureMeeting(testSet1, plus1Year);
        assertTrue(cm3.getFutureMeetingList(con3).isEmpty());
    }

    @Test
    public void testsOneFutureMeetingForGivenContact() {
        cm1.addFutureMeeting(testSet1, plus1Day);
        List<Meeting> testList = cm1.getFutureMeetingList(con1);
        assertEquals(1, testList.size());
        assertEquals(plus1Day, testList.get(0).getDate());
        assertEquals(testSet1, testList.get(0).getContacts());
    }

    @Test
    public void testsTwoFutureMeetingsForContact() {
        cm3.addFutureMeeting(testSet1, plus1Year);
        cm3.addFutureMeeting(testSet2, plus1Day);
        List<Meeting> testList = cm3.getFutureMeetingList(con1);
        assertEquals(2, testList.size());
        assertEquals(plus1Day, testList.get(0).getDate());
        assertEquals(plus1Year, testList.get(1).getDate());
    }

    @Test
    public void testsThreeFutureMeetingsForContact() {
        cm2.addFutureMeeting(testSet2, plus1Year);
        cm2.addFutureMeeting(testSet2, plus1Month);
        cm2.addFutureMeeting(testSet2, plus1Day);
        List<Meeting> testList = cm2.getFutureMeetingList(con2);
        assertEquals(3, testList.size());
        assertEquals(plus1Year, testList.get(2).getDate());
        assertEquals(plus1Month, testList.get(1).getDate());
        assertEquals(plus1Day, testList.get(0).getDate());
    }

    @Test
    public void testsOnlyAddingFutureMeetingsToList() {
        cm2.addFutureMeeting(testSet2, plus1Year);
        cm2.addFutureMeeting(testSet2, plus1Month);
        cm2.addFutureMeeting(testSet2, plus1Day);
        cm2.addNewPastMeeting(testSet2, minus1Hour, "test");
        cm2.addNewPastMeeting(testSet2, minus1Day, "test2");
        List<Meeting> testList = cm2.getFutureMeetingList(con2);
        assertEquals(3, testList.size());
        assertEquals(plus1Day, testList.get(0).getDate());
        assertEquals(plus1Month, testList.get(1).getDate());
        assertEquals(plus1Year, testList.get(2).getDate());
    }

    @Test
    public void testsTwoContactsWithSameMeetingsReturnSameList() {
        cm3.addFutureMeeting(testSet3, plus1Day);
        cm3.addFutureMeeting(testSet3, plus1Month);
        cm3.addFutureMeeting(testSet3, plus1Year);
        assertEquals(cm3.getFutureMeetingList(con1), cm3.getFutureMeetingList(con2));
        assertEquals(cm3.getFutureMeetingList(con3), cm3.getFutureMeetingList(con1));
    }

    @Test
    public void testsListSortingWithSixMeetings() {
        cm1.addFutureMeeting(testSet1, plus1Year);
        cm1.addFutureMeeting(testSet1, plus1Month);
        cm1.addFutureMeeting(testSet1, plus1Day);
        cm1.addFutureMeeting(testSet1, plus1Hour);
        cm1.addFutureMeeting(testSet1, plus1Min);
        cm1.addFutureMeeting(testSet1, plus1Sec);

        List<Meeting> testList = cm1.getFutureMeetingList(con1);
        assertEquals(6, testList.size());
        assertEquals(plus1Sec, testList.get(0).getDate());
        assertEquals(plus1Min, testList.get(1).getDate());
        assertEquals(plus1Hour, testList.get(2).getDate());
        assertEquals(plus1Day, testList.get(3).getDate());
        assertEquals(plus1Month, testList.get(4).getDate());
        assertEquals(plus1Year, testList.get(5).getDate());
        for (Meeting m : testList) {
            assertTrue(m.getContacts().equals(testSet1));
        }
    }


    @Test
    public void testsIDsForReturnedList() {
        cm3.addFutureMeeting(testSet2, plus1Min);
        cm3.addFutureMeeting(testSet1, plus1Min);
        cm3.addFutureMeeting(testSet1, plus1Min);
        cm3.addFutureMeeting(testSet2, plus1Day);
        cm3.addFutureMeeting(testSet2, plus1Day);
        cm3.addFutureMeeting(testSet2, plus1Month);
        cm3.addFutureMeeting(testSet3, plus1Month);
        cm3.addFutureMeeting(testSet2, plus1Year);
        cm3.addFutureMeeting(testSet1, plus1Year);
        List<Meeting> testList = cm3.getFutureMeetingList(con1);
        assertEquals(9, testList.size());
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i).getId(), i + 1);
        }
    }

    @Test
    public void testsFutureMeetingNowPast() {
        cm1.addFutureMeeting(testSet1, plus1Day);
        assertEquals(1, cm1.getFutureMeetingList(con1).size());
        di.changeDate(plus1Month);
        assertTrue(cm1.getFutureMeetingList(con1).isEmpty());
    }

    @Test
    public void testsMultipleMeetingsSomeNowPastSomeStillFuture() {
        cm3.addFutureMeeting(testSet2, plus1Min);
        cm3.addFutureMeeting(testSet1, plus1Hour);
        cm3.addFutureMeeting(testSet3, plus1Month);
        assertEquals(3, cm3.getFutureMeetingList(con1).size());
        di.changeDate(plus1Day);
        assertEquals(1, cm3.getFutureMeetingList(con1).size());
    }

    /**
     * Test getPastMeetingList(Contact)
     */

    @Test(expected = NullPointerException.class)
    public void testNullContactUsed() {
        cm1.addNewPastMeeting(testSet1, minus1Day, "test");
        cm1.getPastMeetingListFor(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastMeetingContactDoesNotExist() {
        cm2.addNewPastMeeting(testSet2, minus1Hour, "notes");
        cm2.getPastMeetingListFor(con3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastListNoContacts() {
        cm.getPastMeetingListFor(con1);
    }

    @Test
    public void testsNoPastMeetings() {
        List<PastMeeting> testList = cm1.getPastMeetingListFor(con1);
        assertTrue(testList.isEmpty());
    }

    @Test
    public void testsOnlyFutureMeetingsAdded() {
        cm3.addFutureMeeting(testSet2, plus1Month);
        cm3.addFutureMeeting(testSet3, plus1Hour);
        assertTrue(cm3.getPastMeetingListFor(con2).isEmpty());
    }

    @Test
    public void testsNoPastMeetingsForSpecifiedContact() {
        cm3.addNewPastMeeting(testSet2, minus1Day, "test1");
        cm3.addNewPastMeeting(testSet1, minus1Year, "test2");
        assertTrue(cm3.getPastMeetingListFor(con3).isEmpty());
    }

    @Test
    public void testsOnePastMeetingForGivenContact() {
        cm1.addNewPastMeeting(testSet1, minus1Day, "meeting notes");
        List<PastMeeting> testList = cm1.getPastMeetingListFor(con1);
        assertEquals(1, testList.size());
        assertEquals(minus1Day, testList.get(0).getDate());
        assertEquals(testSet1, testList.get(0).getContacts());
        assertEquals("meeting notes", testList.get(0).getNotes());
    }

    @Test
    public void testsTwoPastMeetingsForContact() {
        cm3.addNewPastMeeting(testSet1, minus1Year, "note 1");
        cm3.addNewPastMeeting(testSet2, minus1Day, "note 2");
        List<PastMeeting> testList = cm3.getPastMeetingListFor(con1);
        assertEquals(2, testList.size());
        assertEquals(minus1Day, testList.get(1).getDate());
        assertEquals(minus1Year, testList.get(0).getDate());
        assertEquals("note 2", testList.get(1).getNotes());
    }

    @Test
    public void testsThreePastMeetingsForContact() {
        cm2.addNewPastMeeting(testSet2, minus1Year, "more notes");
        cm2.addNewPastMeeting(testSet2, minus1Month, "even more notes");
        cm2.addNewPastMeeting(testSet2, minus1Day, "");
        List<PastMeeting> testList = cm2.getPastMeetingListFor(con2);
        assertEquals(3, testList.size());
        assertEquals(minus1Day, testList.get(2).getDate());
        assertEquals(minus1Month, testList.get(1).getDate());
        assertEquals(minus1Year, testList.get(0).getDate());
        assertEquals("", testList.get(2).getNotes());
        assertEquals("even more notes", testList.get(1).getNotes());
    }

    @Test
    public void testsOnlyAddingPastMeetingsToList() {
        cm2.addFutureMeeting(testSet2, plus1Year);
        cm2.addFutureMeeting(testSet2, plus1Month);
        cm2.addNewPastMeeting(testSet2, minus1Min, "");
        cm2.addNewPastMeeting(testSet2, minus1Hour, "test");
        cm2.addNewPastMeeting(testSet2, minus1Day, "test2");
        List<PastMeeting> testList = cm2.getPastMeetingListFor(con2);
        assertEquals(3, testList.size());
        assertEquals(minus1Day, testList.get(0).getDate());
        assertEquals(minus1Hour, testList.get(1).getDate());
        assertEquals(minus1Min, testList.get(2).getDate());
        assertEquals("test2", testList.get(0).getNotes());
    }

    @Test
    public void testsTwoContactsWithSameMeetingsReturnSamePastMeetingList() {
        cm3.addNewPastMeeting(testSet3, minus1Day, "my notes");
        cm3.addNewPastMeeting(testSet3, minus1Month, "your notes");
        cm3.addNewPastMeeting(testSet3, minus1Year, "our notes");
        assertEquals(cm3.getPastMeetingListFor(con1), cm3.getPastMeetingListFor(con2));
        assertEquals(cm3.getPastMeetingListFor(con3), cm3.getPastMeetingListFor(con1));
        assertEquals(cm3.getPastMeetingListFor(con2), cm3.getPastMeetingListFor(con3));
    }

    @Test
    public void testsListSortingWithSixPastMeetings() {
        cm1.addNewPastMeeting(testSet1, minus1Year, "first");
        cm1.addNewPastMeeting(testSet1, minus1Month, "second");
        cm1.addNewPastMeeting(testSet1, minus1Day, "third");
        cm1.addNewPastMeeting(testSet1, minus1Hour, "fourth");
        cm1.addNewPastMeeting(testSet1, minus1Min, "fifth");
        cm1.addNewPastMeeting(testSet1, minus1Sec, "sixth");
        List<PastMeeting> testList = cm1.getPastMeetingListFor(con1);
        assertEquals(6, testList.size());
        assertEquals(minus1Sec, testList.get(5).getDate());
        assertEquals(minus1Min, testList.get(4).getDate());
        assertEquals(minus1Hour, testList.get(3).getDate());
        assertEquals(minus1Day, testList.get(2).getDate());
        assertEquals(minus1Month, testList.get(1).getDate());
        assertEquals(minus1Year, testList.get(0).getDate());
        for (Meeting m : testList) {
            assertTrue(m.getContacts().equals(testSet1));
        }
    }


    @Test
    public void testsMultipleSimilarMeetings() {
        cm3.addNewPastMeeting(testSet2, minus1Sec, "");
        cm3.addNewPastMeeting(testSet2, minus1Sec, "meeting");
        cm3.addNewPastMeeting(testSet1, minus1Sec, "same time different contacts");
        cm3.addNewPastMeeting(testSet3, minus1Min, "test");
        cm3.addNewPastMeeting(testSet3, minus1Min, "test");
        cm3.addNewPastMeeting(testSet3, minus1Min, "another test");
        cm3.addNewPastMeeting(testSet2, minus1Min, "different contacts same time");
        cm3.addNewPastMeeting(testSet1, minus1Hour, "unique meeting");
        List<PastMeeting> testList = cm3.getPastMeetingListFor(con1);
        assertEquals(8, testList.size());
        assertEquals("unique meeting", testList.get(0).getNotes());
        assertEquals(minus1Min, testList.get(1).getDate());
        assertEquals(minus1Sec, testList.get(5).getDate());
    }

    @Test
    public void testsGetListForFutureMeetingNowPast() {
        cm2.addFutureMeeting(testSet1, plus1Day);
        assertTrue(cm2.getPastMeetingListFor(con1).isEmpty());
        di.changeDate(plus1Month);
        assertEquals(1, cm2.getPastMeetingListFor(con1).size());
        assertEquals(plus1Day, cm2.getPastMeetingListFor(con1).get(0).getDate());
    }

    @Test
    public void testsMixOfFutureAndPastWithSomeFutureBecomingPast() {
        cm3.addNewPastMeeting(testSet1, minus1Day, "meeting1");
        cm3.addFutureMeeting(testSet2, plus1Sec);
        cm3.addFutureMeeting(testSet2, plus1Min);
        cm3.addFutureMeeting(testSet3, plus1Hour);
        cm3.addFutureMeeting(testSet1, plus1Month);
        assertEquals(1, cm3.getPastMeetingListFor(con1).size());
        di.changeDate(plus1Day);
        assertEquals(4, cm3.getPastMeetingListFor(con1).size());
        assertEquals(minus1Day, cm3.getPastMeetingListFor(con1).get(0).getDate());
        assertEquals(plus1Min, cm3.getPastMeetingListFor(con1).get(2).getDate());
        assertEquals(plus1Hour, cm3.getPastMeetingListFor(con1).get(3).getDate());
    }

    /**
     * Test getMeetingListOn(Calendar date)
     */

    @Test(expected = NullPointerException.class)
    public void testsNullDate() {
        cm2.getMeetingListOn(null);
    }

    @Test
    public void testsNoMeetings() {
        assertTrue(cm1.getMeetingListOn(plus1Day).isEmpty());
    }

    @Test
    public void testsNoMeetingsOnDateGiven() {
        cm3.addFutureMeeting(testSet1, plus1Month);
        cm3.addFutureMeeting(testSet3, plus1Year);
        assertTrue(cm3.getMeetingListOn(plus1Hour).isEmpty());
        assertTrue(cm3.getMeetingListOn(minus1Day).isEmpty());
    }

    @Test
    public void testsOneMeeting() {
        cm1.addFutureMeeting(testSet1, plus1Day);
        List<Meeting> testList = cm1.getMeetingListOn(plus1Day);
        assertEquals(1, testList.size());
        assertEquals(testSet1, testList.get(0).getContacts());
    }

    @Test
    public void testsTwoMeetingsOnSameDay() {
        cm2.addFutureMeeting(testSet2, plus1Hour);
        cm2.addFutureMeeting(testSet2, plus1Min);
        List<Meeting> testList = cm2.getMeetingListOn(plus1Hour);
        assertEquals(2, testList.size());
        assertEquals(testSet2, testList.get(0).getContacts());
        assertEquals(testSet2, testList.get(1).getContacts());
    }

    @Test
    public void testsThreeMeetings() {
        cm3.addFutureMeeting(testSet1, plus1Hour);
        cm3.addFutureMeeting(testSet3, plus1Min);
        cm3.addFutureMeeting(testSet2, plus1Sec);
        List<Meeting> testList = cm3.getMeetingListOn(current);
        assertEquals(3, testList.size());
        assertEquals(plus1Sec, testList.get(0).getDate());
        assertEquals(plus1Min, testList.get(1).getDate());
        assertEquals(plus1Hour, testList.get(2).getDate());
    }

    @Test
    public void testsThreePastMeetings() {
        cm3.addNewPastMeeting(testSet2, minus1Sec, "1");
        cm3.addNewPastMeeting(testSet3, minus1Hour, "2");
        cm3.addNewPastMeeting(testSet1, minus1Min, "3");
        List<Meeting> testList = cm3.getMeetingListOn(current);
        assertEquals(3, testList.size());
        assertEquals(minus1Hour, testList.get(0).getDate());
        assertEquals(minus1Min, testList.get(1).getDate());
        assertEquals(minus1Sec, testList.get(2).getDate());
    }

    @Test
    public void testsFutureAndPastOnSameDay() {
        cm3.addFutureMeeting(testSet1, plus1Hour);
        cm3.addNewPastMeeting(testSet2, minus1Hour, "test");
        List<Meeting> testList = cm3.getMeetingListOn(current);
        assertEquals(2, testList.size());
        assertEquals(minus1Hour, testList.get(0).getDate());
        assertEquals(plus1Hour, testList.get(1).getDate());
    }

    @Test
    public void testsChronologyWithSixMeetingsOnSameDay() {
        cm3.addFutureMeeting(testSet2, plus1Hour);
        cm3.addFutureMeeting(testSet1, plus1Min);
        cm3.addFutureMeeting(testSet3, plus1Sec);
        cm3.addNewPastMeeting(testSet1, minus1Sec, "test1");
        cm3.addNewPastMeeting(testSet2, minus1Min, "test2");
        cm3.addNewPastMeeting(testSet3, minus1Hour, "test3");
        List<Meeting> testList = cm3.getMeetingListOn(current);
        assertEquals(6, testList.size());
        assertEquals(minus1Hour, testList.get(0).getDate());
        assertEquals(minus1Min, testList.get(1).getDate());
        assertEquals(minus1Sec, testList.get(2).getDate());
        assertEquals(plus1Sec, testList.get(3).getDate());
        assertEquals(plus1Min, testList.get(4).getDate());
        assertEquals(plus1Hour, testList.get(5).getDate());
    }


    @Test
    public void testsMultipleDates() {
        cm3.addFutureMeeting(testSet2, plus1Hour);
        cm3.addFutureMeeting(testSet2, plus1Hour);
        cm3.addFutureMeeting(testSet1, plus1Hour);
        cm3.addNewPastMeeting(testSet3, minus1Min, "test");
        cm3.addNewPastMeeting(testSet3, minus1Min, "test duplicate");
        cm3.addNewPastMeeting(testSet3, minus1Min, "another test duplicate");
        cm3.addNewPastMeeting(testSet2, minus1Min, "different contacts same time");
        cm3.addNewPastMeeting(testSet1, minus1Hour, "unique meeting");
        List<Meeting> testList = cm3.getMeetingListOn(current);
        assertEquals(8, testList.size());
        assertEquals(minus1Hour, testList.get(0).getDate());
        assertEquals(minus1Min, testList.get(1).getDate());
        assertEquals(minus1Min, testList.get(4).getDate());
        assertEquals(plus1Hour, testList.get(5).getDate());
        assertEquals(plus1Hour, testList.get(7).getDate());
    }

    /**
     * Test addMeetingNotes()
     */

    @Test(expected = NullPointerException.class)
    public void testsNullNotes() {
        cm1.addNewPastMeeting(testSet1, minus1Hour, "");
        cm1.addMeetingNotes(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsNoMeetingsExist() {
        cm1.addMeetingNotes(1, "no meeting");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsWrongMeetingId() {
        cm1.addNewPastMeeting(testSet1, minus1Hour, "");
        cm1.addMeetingNotes(2, "wrong id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsZeroId() {
        cm1.addNewPastMeeting(testSet1, minus1Day, "");
        cm1.addMeetingNotes(0, "zero id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsNegativeId() {
        cm2.addNewPastMeeting(testSet2, minus1Month, "");
        cm2.addMeetingNotes(-1, "negative id");
    }

    @Test(expected = IllegalStateException.class)
    public void testsMeetingStillInFuture() {
        cm1.addFutureMeeting(testSet1, plus1Year);
        cm1.addMeetingNotes(1, "not happened yet");
    }

    @Test
    public void testsAddExtraNotesToPastMeeting() {
        //assumption is that this will overwrite existing notes
        cm1.addNewPastMeeting(testSet1, minus1Month, "notes");
        PastMeeting pm = cm1.addMeetingNotes(1, "more notes");
        assertEquals("more notes", pm.getNotes());
        assertEquals("more notes", cm1.getPastMeetingListFor(con1).get(0).getNotes());
    }

    @Test
    public void testsAddExtraNotesWhenNoneBefore() {
        cm2.addNewPastMeeting(testSet2, minus1Day, "");
        cm2.addMeetingNotes(1, "new notes");
        assertEquals("new notes", cm2.getPastMeetingListFor(con2).get(0).getNotes());
    }

    @Test
    public void testsAddNotesToMultiplePastMeetings() {
        cm3.addNewPastMeeting(testSet2, minus1Hour, "notes");
        cm3.addNewPastMeeting(testSet2, minus1Day, "");
        cm3.addNewPastMeeting(testSet3, minus1Month, "3");
        PastMeeting pm = cm3.addMeetingNotes(3, "success");
        cm3.addMeetingNotes(1, "");
        cm3.addMeetingNotes(2, "cancelled");
        assertEquals("success", pm.getNotes());
        assertEquals("cancelled", cm3.getPastMeetingListFor(con2).get(1).getNotes());
        assertEquals("", cm3.getPastMeetingListFor(con1).get(2).getNotes());
    }


    @Test
    public void testsMultipleFutureMeetingsNowPast() {
        cm3.addFutureMeeting(testSet1, plus1Year);
        cm3.addFutureMeeting(testSet2, plus1Day);
        cm3.addFutureMeeting(testSet3, plus1Month);
        di.changeDate(new GregorianCalendar(2020, 10, 3));
        PastMeeting pm1 = cm3.addMeetingNotes(1, "first");
        cm3.addMeetingNotes(2, "second");
        cm3.addMeetingNotes(3, "third");
        assertTrue(cm3.getFutureMeetingList(con1).isEmpty());
        assertEquals(3, cm3.getPastMeetingListFor(con1).size());
        assertEquals("first", pm1.getNotes());
        assertTrue(cm3.getFutureMeetingList(con1).isEmpty());
        assertNotNull(cm3.getPastMeeting(1));
    }

    @Test
    public void testsMultipleFutureMeetingsNowPastAndSomePastMeetings() {
        cm3.addFutureMeeting(testSet2, plus1Month);
        cm3.addFutureMeeting(testSet1, plus1Day);
        cm3.addNewPastMeeting(testSet2, minus1Month, "original notes");
        cm3.addNewPastMeeting(testSet3, minus1Day, "original notes 2");
        assertEquals(2, cm3.getPastMeetingListFor(con1).size());
        di.changeDate(plus1Year);
        cm3.addMeetingNotes(1, "past 1");
        cm3.addMeetingNotes(2, "past 2");
        PastMeeting pm3 = cm3.addMeetingNotes(3, "past 3");
        PastMeeting pm4 = cm3.addMeetingNotes(4, "past 4");
        assertTrue(cm3.getFutureMeetingList(con1).isEmpty());
        assertEquals("past 3", pm3.getNotes());
        assertEquals("past 4", pm4.getNotes());
        assertEquals(4, cm3.getPastMeetingListFor(con1).size());
    }


    /**
     * Test flush()
     */

    @Test
    public void testNoDataSaved() {
        cm.flush();
        ContactManager test = new ContactManagerImpl();
        assertTrue(test.getContacts("").isEmpty());
    }

    @Test
    public void testsOneContactSaved() {
        cm1.flush();
        cm = new ContactManagerImpl();
        assertEquals(1, cm.getContacts("").size());
        assertTrue(cm.getContacts(1).contains(con1));
    }

    @Test
    public void testsMultipleContactsSaved() {
        cm3.flush();
        cm = new ContactManagerImpl();
        assertEquals(3, cm.getContacts("").size());
        assertEquals(cm3.getContacts(""), cm.getContacts(""));
    }

    @Test
    public void testsFutureMeetingsSaved() {
        cm3.addFutureMeeting(testSet1, plus1Day);
        cm3.addFutureMeeting(testSet3, plus1Month);
        cm3.flush();
        cm = new ContactManagerImpl();
        assertEquals(2, cm.getFutureMeetingList(con1).size());
        assertEquals(plus1Month, cm.getFutureMeeting(2).getDate());
        assertEquals(plus1Day, cm.getFutureMeetingList(con1).get(0).getDate());
    }

    @Test
    public void testsPastMeetingsSaved() {
        cm3.addNewPastMeeting(testSet1, minus1Hour, "Past meeting");
        cm3.addNewPastMeeting(testSet2, minus1Day, "other");
        cm3.flush();
        cm = new ContactManagerImpl();
        assertEquals(2, cm.getPastMeetingListFor(con1).size());
        assertEquals("Past meeting", cm.getPastMeetingListFor(con1).get(1).getNotes());
        assertEquals("other", cm.getPastMeeting(2).getNotes());
    }

    @Test
    public void testsFutureAndPastTogether() {
        cm2.addFutureMeeting(testSet1, plus1Day);
        cm2.addNewPastMeeting(testSet1, minus1Day, "");
        cm2.flush();
        cm = new ContactManagerImpl();
        assertEquals(new PastMeetingImpl(2, minus1Day, testSet1, ""), cm.getPastMeeting(2));
        assertEquals(plus1Day, cm.getMeeting(1).getDate());
    }

    @Test
    public void testsOverWrite() {
        cm3.addFutureMeeting(testSet1, plus1Month);
        cm3.addFutureMeeting(testSet1, plus1Min);
        cm3.flush();
        cm = new ContactManagerImpl();
        assertEquals(2, cm.getFutureMeetingList(con1).size());
        di.changeDate(plus1Hour);
        cm3.addFutureMeeting(testSet2, plus1Day);
        cm3.addFutureMeeting(testSet3, plus1Year);
        cm3.flush();
        cm = new ContactManagerImpl();
        assertEquals(3, cm.getFutureMeetingList(con1).size());
        assertEquals(1, cm.getPastMeetingListFor(con1).size());
        assertEquals(plus1Year, cm.getFutureMeetingList(con1).get(2).getDate());
    }

    @Test
    public void checkIdCountWorkingAfterSaving() {
        cm3.flush();
        cm = new ContactManagerImpl();
        int id = cm.addNewContact("new guy", "test");
        assertEquals(4, id);
        cm.flush();
        cm2 = new ContactManagerImpl();
        id = cm2.addNewContact("another new guy", "another test");
        assertEquals(5, id);
    }

    @Test
    public void checkMeetingIdCountAfterSaving() {
        cm1.addFutureMeeting(testSet1, plus1Day);
        cm1.flush();
        cm = new ContactManagerImpl();
        int id = cm.addFutureMeeting(testSet1, plus1Month);
        assertEquals(2, id);
        cm.flush();
        cm2 = new ContactManagerImpl();
        id = cm2.addFutureMeeting(testSet1, plus1Month);
        assertEquals(3, id);
    }

    @Test
    public void testsMeetingIdCountAfterFlushPastAndFutureAndDifferentContacts() {
        cm3.addFutureMeeting(testSet1, plus1Day);
        cm3.addFutureMeeting(testSet1, plus1Month);
        cm3.addNewPastMeeting(testSet3, minus1Day, "meet");
        cm3.addNewPastMeeting(testSet2, minus1Hour, "meet2");
        cm3.flush();
        cm = new ContactManagerImpl();
        assertEquals(5, cm.addFutureMeeting(testSet2, plus1Month));
        cm.addNewPastMeeting(testSet3, minus1Hour, "");
        cm.flush();
        assertNotEquals(3, cm1.getContacts("").size());
        cm1 = new ContactManagerImpl();
        assertEquals(3, cm1.getContacts("").size());
        assertEquals(7, cm1.addFutureMeeting(testSet3, plus1Day));
        di.changeDate(plus1Year);
        assertEquals(7, cm1.getPastMeetingListFor(con1).size());
    }

    @Test
    public void checkOldContactsOverwritten() {
        cm1.flush();
        cm3 = new ContactManagerImpl();
        assertEquals(1, cm3.getContacts("").size());
    }

}