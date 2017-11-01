import java.io.Serializable;

/**
 * Created by James Pickles on 22/02/2016.
 */
public class ContactImpl implements Contact, Serializable {
    private int contactId;
    private String contactName;
    private String contactNotes;

    public ContactImpl(int id, String name, String notes) {
        if (name == null || notes == null) {
            throw new NullPointerException("Null parameters cannot be passed to constructor.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a non zero positive integer.");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("Contact name must not be empty.");
        }
        contactId = id;
        contactName = name;
        contactNotes = notes;
    }

    public ContactImpl(int id, String name) {
        if (name == null) {
            throw new NullPointerException("Null parameters cannot be passed to constructor.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a non zero positive integer.");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("Contact name must not be empty.");
        }
        contactId = id;
        contactName = name;
        contactNotes = "";
    }

    /**
     * @see Contact#getId()
     */
    public int getId() {
        return contactId;
    }

    /**
     * @see Contact#getName()
     */
    public String getName() {
        return contactName;
    }

    /**
     * @see Contact#getNotes()
     */
    public String getNotes() {
        return contactNotes;
    }

    /**
     * @see Contact#addNotes(String)
     */
    public void addNotes(String note) {
        if (contactNotes.equals("")) {
            contactNotes = note;
        } else {
            contactNotes += "; " + note;
        }
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ContactImpl && this.hashCode() == other.hashCode();
    }
}
