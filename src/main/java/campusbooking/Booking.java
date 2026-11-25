package campusbooking;

public class Booking {
    private Student student;
    private CampusEvent event;

    public Booking(Student student, CampusEvent event) {
        this.student = student;
        this.event = event;
    }

    public CampusEvent getEvent() {
        return event;
    }

    public String toString() {
        return student.getName() + " | " + student.getEmail()
                + " | " + event.getEventName();
    }
}
