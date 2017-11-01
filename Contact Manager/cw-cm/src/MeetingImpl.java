import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by James Pickles on 22/02/2016.
 */
public abstract class MeetingImpl implements Meeting, Serializable {
    private int meetingId;
    private Calendar meetingDate;
    private Set<Contact> meetingAttendees;


    public MeetingImpl(int id, Calendar date, Set<Contact> attendees) {
        if (id <= 0) {
            throw new IllegalArgumentException("Meeting ID must be a non zero positive integer.");
        }
        if (date == null || attendees == null) {
            throw new NullPointerException("Null parameters cannot be passed to constructor.");
        }
        if (attendees.isEmpty()) {
            throw new IllegalArgumentException("Set of contacts must not be empty.");
        }
        meetingId = id;
        meetingDate = date;
        meetingAttendees = attendees;
    }

    /**
     * @see Meeting#getId()
     */
    public int getId() {
        return meetingId;
    }

    /**
     * @see Meeting#getDate()
     */
    public Calendar getDate() {
        return meetingDate;
    }

    /**
     * @see Meeting#getContacts()
     */
    public Set<Contact> getContacts() {
        return meetingAttendees;
    }
}
