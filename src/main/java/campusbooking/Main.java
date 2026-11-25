package campusbooking;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingWindow window = new BookingWindow();
            window.setVisible(true);
        });
    }
}
