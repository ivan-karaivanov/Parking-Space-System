import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class ParkingBookingSystemGUI extends JFrame {
    private static final int TOTAL_PARKING_SPACES = 30;
    private static final int MAX_BOOKING_DURATION_IN_HOURS = 4;
    private static boolean[] isParkingSpaceAvailable = new boolean[TOTAL_PARKING_SPACES];
    private JTextField licensePlateNumberTextField;
    private JComboBox<Integer> parkingSpaceComboBox;
    private JComboBox<Integer> bookingDurationComboBox;
    private JLabel remainingSpacesLabel;
    private Set<String> registeredLicensePlates;

    public ParkingBookingSystemGUI() {
        super("Parking Booking System");

        // Initialize GUI components
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new GridLayout(3, 2));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(new JLabel("License Plate Number:"));
        licensePlateNumberTextField = new JTextField();
        topPanel.add(licensePlateNumberTextField);
        topPanel.add(new JLabel("Parking Space Number:"));
        parkingSpaceComboBox = new JComboBox<>();
        topPanel.add(parkingSpaceComboBox);
        topPanel.add(new JLabel("Booking Duration (hours):"));
        bookingDurationComboBox = new JComboBox<>();
        for (int i = 1; i <= MAX_BOOKING_DURATION_IN_HOURS; i++) {
            bookingDurationComboBox.addItem(i);
        }
        topPanel.add(bookingDurationComboBox);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(Color.WHITE);
        JButton bookButton = new JButton("<html><font color='#FF8888'><b>BOOK</b></font></html>");
        bookButton.setBackground(Color.RED);
        bookButton.setForeground(Color.LIGHT_GRAY);
        centerPanel.add(bookButton);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        remainingSpacesLabel = new JLabel("Remaining Parking Spaces: " + getTotalAvailableSpaces());
        bottomPanel.add(remainingSpacesLabel);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add listeners
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String licensePlateNumber = licensePlateNumberTextField.getText();
                if (licensePlateNumber.length() < 5) {
                    JOptionPane.showMessageDialog(null, "License Plate Number should have a minimum of 5 characters.",
                            "Invalid License Plate", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (registeredLicensePlates.contains(licensePlateNumber)) {
                    JOptionPane.showMessageDialog(null, "This number plate is already registered. Please try a different one.",
                            "Duplicate License Plate", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int parkingSpaceNumber = (int) parkingSpaceComboBox.getSelectedItem();
                int bookingDurationInHours = (int) bookingDurationComboBox.getSelectedItem();

                JOptionPane.showMessageDialog(null, "Your booking details:\n" +
                                "License Plate Number: " + licensePlateNumber + "\n" +
                                "Parking Space Number: " + parkingSpaceNumber + "\n" +
                                "Booking Duration: " + bookingDurationInHours + " hours",
                        "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);

                isParkingSpaceAvailable[parkingSpaceNumber - 1] = false;
                parkingSpaceComboBox.removeItem(parkingSpaceNumber);
                remainingSpacesLabel.setText("Remaining Parking Spaces: " + getTotalAvailableSpaces());

                registeredLicensePlates.add(licensePlateNumber);

                if (parkingSpaceComboBox.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Sorry, no parking space is available right now.",
                            "Booking Error", JOptionPane.ERROR_MESSAGE);
                    parkingSpaceComboBox.setEnabled(false);
                    bookButton.setEnabled(false);
                }
            }
        });

        // Initialize parking space ComboBox
        for (int i = 1; i <= TOTAL_PARKING_SPACES; i++) {
            parkingSpaceComboBox.addItem(i);
        }

        registeredLicensePlates = new HashSet<>();
    }

    private static void initializeParkingSpaces() {
        for (int i = 0; i < TOTAL_PARKING_SPACES; i++) {
            isParkingSpaceAvailable[i] = true;
        }
    }

    private int getTotalAvailableSpaces() {
        int count = 0;
        for (boolean available : isParkingSpaceAvailable) {
            if (available) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        initializeParkingSpaces();

        // Set look and feel to Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, use the default look and feel
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ParkingBookingSystemGUI gui = new ParkingBookingSystemGUI();
                gui.setVisible(true);
            }
        });
    }
}
