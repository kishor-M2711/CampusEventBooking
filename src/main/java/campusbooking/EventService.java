package campusbooking;

import java.util.ArrayList;

public class EventService {
    private ArrayList<CampusEvent> events = new ArrayList<>();

    public EventService() {
        events.add(new CampusEvent("Tech Fest", "2026-12-10", "Main Auditorium", 50));
        events.add(new CampusEvent("Coding Contest", "2026-12-15", "Lab 2", 30));
        events.add(new CampusEvent("Cultural Day", "2026-12-20", "Open Ground", 100));
    }

    public void addEvent(CampusEvent event) {
        events.add(event);
    }

    public ArrayList<CampusEvent> getEvents() {
        return events;
    }
}
