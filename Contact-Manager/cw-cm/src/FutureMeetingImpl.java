import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by James Pickles on 23/02/2016.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {

    public FutureMeetingImpl(int id, Calendar date, Set<Contact> attendees) {
        super(id, date, attendees);
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof FutureMeetingImpl && this.hashCode() == other.hashCode();
    }
}
