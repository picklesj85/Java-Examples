import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by James Pickles on 22/02/2016.
 */
public class ContactImplTest {
    Contact jim, ben, sam, will, jen, max, dan, jane, kay, lee;

    @Before
    public void setUp() {
        jim = new ContactImpl(1, "Jim", "test contact 1");
        ben = new ContactImpl(2, "Ben", "test contact 2");
        sam = new ContactImpl(3, "Sam", "test contact 3");
        will = new ContactImpl(4, "Will", "test contact 4");
        jen = new ContactImpl(5, "Jen", "test contact 5");
        max = new ContactImpl(6, "Max");
        dan = new ContactImpl(7, "Dan");
        jane = new ContactImpl(8, "Jane");
        kay = new ContactImpl(9, "Kay");
        lee = new ContactImpl(10, "Lee");
    }

    @Test
    public void testsGetIDforJim() {
        assertEquals(1, jim.getId());
    }
    @Test
    public void testsGetIDforBen() {
        assertEquals(2, ben.getId());
    }
    @Test
    public void testsGetIDforSam() {
        assertEquals(3, sam.getId());
    }
    @Test
    public void testsGetIDforWill() {
        assertEquals(4, will.getId());
    }
    @Test
    public void testsGetIDforJen() {
        assertEquals(5, jen.getId());
    }
    @Test
    public void testsGetIDforMax() {
        assertEquals(6, max.getId());
    }
    @Test
    public void testsGetIDforDan() {
        assertEquals(7, dan.getId());
    }
    @Test
    public void testsGetIDforJane() {
        assertEquals(8, jane.getId());
    }
    @Test
    public void testsGetIDforKay() {
        assertEquals(9, kay.getId());
    }
    @Test
    public void testsGetIDforLee() {
        assertEquals(10, lee.getId());
    }


    @Test
    public void testGetNameJim() {
        assertEquals("Jim", jim.getName());
    }
    @Test
    public void testGetNameBen() {
        assertEquals("Ben", ben.getName());
    }
    @Test
    public void testGetNameSam() {
        assertEquals("Sam", sam.getName());
    }
    @Test
    public void testGetNameWill() {
        assertEquals("Will", will.getName());
    }
    @Test
    public void testGetNameJen() {
        assertEquals("Jen", jen.getName());
    }
    @Test
    public void testGetNameMax() {
        assertEquals("Max", max.getName());
    }
    @Test
    public void testGetNameDan() {
        assertEquals("Dan", dan.getName());
    }
    @Test
    public void testGetNameJane() {
        assertEquals("Jane", jane.getName());
    }
    @Test
    public void testGetNameKay() {
        assertEquals("Kay", kay.getName());
    }
    @Test
    public void testGetNameLee() {
        assertEquals("Lee", lee.getName());
    }


    @Test
    public void testGetNotesJim() {
        assertEquals("test contact 1", jim.getNotes());
    }
    @Test
    public void testGetNotesBen() {
        assertEquals("test contact 2", ben.getNotes());
    }
    @Test
    public void testGetNotesSam() {
        assertEquals("test contact 3", sam.getNotes());
    }
    @Test
    public void testGetNotesWill() {
        assertEquals("test contact 4", will.getNotes());
    }
    @Test
    public void testGetNotesJen() {
        assertEquals("test contact 5", jen.getNotes());
    }
    @Test
    public void testGetNotesMax() {
        assertEquals("", max.getNotes());
    }
    @Test
    public void testGetNotesDan() {
        assertEquals("", dan.getNotes());
    }
    @Test
    public void testGetNotesJane() {
        assertEquals("", jane.getNotes());
    }
    @Test
    public void testGetNotesKay() {
        assertEquals("", kay.getNotes());
    }
    @Test
    public void testGetNotesLee() {
        assertEquals("", lee.getNotes());
    }


    @Test
    public void testAddNotesJim() {
        jim.addNotes("adding extra notes to Jim");
        assertEquals("test contact 1; adding extra notes to Jim", jim.getNotes());
    }
    @Test
    public void testAddNotesBen() {
        ben.addNotes("adding extra notes to Ben");
        assertEquals("test contact 2; adding extra notes to Ben", ben.getNotes());
    }
    @Test
    public void testAddNotesSam() {
        sam.addNotes("adding extra notes to Sam");
        assertEquals("test contact 3; adding extra notes to Sam", sam.getNotes());
    }
    @Test
    public void testAddNotesWill() {
        will.addNotes("adding extra notes to Will");
        assertEquals("test contact 4; adding extra notes to Will", will.getNotes());
    }
    @Test
    public void testAddNotesJen() {
        jen.addNotes("adding extra notes to Jen");
        assertEquals("test contact 5; adding extra notes to Jen", jen.getNotes());
    }
    @Test
    public void testAddNotesMax() {
        max.addNotes("adding notes to Max");
        assertEquals("adding notes to Max", max.getNotes());
    }
    @Test
    public void testAddTwoNotesDan() {
        dan.addNotes("adding notes to Dan");
        dan.addNotes("adding some more notes to Dan");
        assertEquals("adding notes to Dan; adding some more notes to Dan", dan.getNotes());
    }
    @Test
    public void testAddThreeNotesJane() {
        jane.addNotes("adding notes to Jane");
        jane.addNotes("adding more notes to Jane");
        jane.addNotes("adding even more notes to Jane");
        assertEquals("adding notes to Jane; adding more notes to Jane; adding even more notes to Jane", jane.getNotes());
    }
    @Test
    public void testAddNotesKay() {
        kay.addNotes("adding notes to Kay");
        kay.addNotes("adding more notes to Kay");
        kay.addNotes("adding even more notes to Kay");
        kay.addNotes("adding last set of notes to Kay");
        assertEquals("adding notes to Kay; adding more notes to Kay; adding even more notes to Kay; adding last set of notes to Kay", kay.getNotes());
    }
    @Test
    public void testAddEmptyNotesLee() {
        lee.addNotes("");
        assertEquals("", lee.getNotes());
    }


    @Test(expected = NullPointerException.class)
    public void testsAddNullParameterToConstructor() {
        new ContactImpl(1, null);
    }

    @Test(expected = NullPointerException.class)
    public void testsAddNullParameterToOtherConstructor() {
        new ContactImpl(1, "Tom", null);
    }

    @Test(expected = NullPointerException.class)
    public void testsAddDifferentNullParameterToConstructor() {
        new ContactImpl(1, null, "notes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddZeroIdToConstructor() {
        new ContactImpl(0, "Test", "Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddZeroIdToOtherConstructor() {
        new ContactImpl(0, "Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNegativeIdToConstructor() {
        new ContactImpl(-2, "Test", "Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNegativeIdToOtherConstructor() {
        new ContactImpl(-1, "Test", "Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyNameToConstructor() {
        new ContactImpl(-1, "", "Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyNameToOtherConstructor() {
        new ContactImpl(-1, "", "Test");
    }
}