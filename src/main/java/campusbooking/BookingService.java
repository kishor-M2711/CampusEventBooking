package campusbooking;

import java.util.ArrayList;

public class BookingService {
    private ArrayList<Booking> bookings = new ArrayList<>();

    public boolean book(Student student, CampusEvent event) {
        if (event.getAvailableSeats() == 0) {
            return false;
        }

        event.bookSeat();
        bookings.add(new Booking(student, event));
        return true;
    }

    public void cancel(int index) {
        Booking booking = bookings.get(index);
        booking.getEvent().cancelSeat();
        bookings.remove(index);
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }
}
