package campusbooking;

public class CampusEvent {
    private String eventName;
    private String eventDate;
    private String location;
    private int availableSeats;

    public CampusEvent(String eventName, String eventDate,
                       String location, int availableSeats) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.location = location;
        this.availableSeats = availableSeats;
    }

    public String getEventName() {
        return eventName;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }

    public void cancelSeat() {
        availableSeats++;
    }

    public String toString() {
        return eventName + " | " + eventDate + " | "
                + location + " | Seats: " + availableSeats;
    }
}
