package campusbooking;

import javax.swing.*;
import java.awt.*;

public class BookingWindow extends JFrame {
    private EventService eventService = new EventService();
    private BookingService bookingService = new BookingService();

    private DefaultListModel<String> eventList = new DefaultListModel<>();
    private DefaultListModel<String> bookingList = new DefaultListModel<>();

    private JList<String> events = new JList<>(eventList);
    private JList<String> bookings = new JList<>(bookingList);

    private JLabel eventCount = new JLabel();
    private JLabel bookingCount = new JLabel();

    public BookingWindow() {
        setTitle("Campus Event Booking System");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", createDashboard());
        tabs.addTab("Events", createEventsPage());
        tabs.addTab("Bookings", createBookingsPage());

        add(tabs);
        refreshLists();
    }

    private JPanel createDashboard() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        JLabel title = new JLabel("Welcome to Campus Events!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        eventCount.setFont(new Font("Arial", Font.BOLD, 20));
        bookingCount.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(title);
        panel.add(eventCount);
        panel.add(bookingCount);
        return panel;
    }

    private JPanel createEventsPage() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        events.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(new JScrollPane(events), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton addButton = new JButton("Add Event");
        JButton bookButton = new JButton("Book Selected Event");

        addButton.addActionListener(e -> addEvent());
        bookButton.addActionListener(e -> bookEvent());

        buttons.add(addButton);
        buttons.add(bookButton);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBookingsPage() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        bookings.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(new JScrollPane(bookings), BorderLayout.CENTER);

        JButton cancelButton = new JButton("Cancel Selected Booking");
        cancelButton.addActionListener(e -> cancelBooking());
        panel.add(cancelButton, BorderLayout.SOUTH);

        return panel;
    }

    private void addEvent() {
        JTextField name = new JTextField();
        JTextField date = new JTextField("2026-12-25");
        JTextField place = new JTextField();
        JTextField seats = new JTextField("40");

        Object[] fields = {
            "Event name:", name,
            "Event date:", date,
            "Location:", place,
            "Available seats:", seats
        };

        int choice = JOptionPane.showConfirmDialog(
                this, fields, "New Campus Event",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            if (name.getText().trim().isEmpty()
                    || date.getText().trim().isEmpty()
                    || place.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            int seatCount = Integer.parseInt(seats.getText().trim());
            if (seatCount < 1) {
                JOptionPane.showMessageDialog(this, "Seats must be at least 1.");
                return;
            }

            CampusEvent event = new CampusEvent(
                    name.getText(), date.getText(),
                    place.getText(), seatCount
            );

            eventService.addEvent(event);
            refreshLists();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid seat number.");
        }
    }

    private void bookEvent() {
        int selected = events.getSelectedIndex();

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select an event first.");
            return;
        }

        JTextField name = new JTextField();
        JTextField email = new JTextField();

        Object[] fields = {
            "Student name:", name,
            "Student email:", email
        };

        int choice = JOptionPane.showConfirmDialog(
                this, fields, "Student Booking",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        if (name.getText().trim().isEmpty()
                || email.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter student details.");
            return;
        }

        CampusEvent selectedEvent = eventService.getEvents().get(selected);
        Student student = new Student(name.getText(), email.getText());

        boolean booked = bookingService.book(student, selectedEvent);

        if (booked) {
            JOptionPane.showMessageDialog(this, "Seat booked successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "No seats are available.");
        }

        refreshLists();
    }

    private void cancelBooking() {
        int selected = bookings.getSelectedIndex();

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a booking first.");
            return;
        }

        bookingService.cancel(selected);
        refreshLists();
        JOptionPane.showMessageDialog(this, "Booking cancelled.");
    }

    private void refreshLists() {
        eventList.clear();

        for (CampusEvent event : eventService.getEvents()) {
            eventList.addElement(event.toString());
        }

        bookingList.clear();

        for (Booking booking : bookingService.getBookings()) {
            bookingList.addElement(booking.toString());
        }

        eventCount.setText("Total Events: " + eventService.getEvents().size());
        bookingCount.setText("Total Bookings: " + bookingService.getBookings().size());
    }
}
