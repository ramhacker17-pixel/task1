import java.util.*;
import java.io.*;

/*
 * Hotel Reservation System
 * Java 8 Compatible
 * Console Based | OOP | File I/O
 */

class Room {
    int roomNumber;
    String category;
    double price;
    boolean isAvailable;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public String toString() {
        return roomNumber + "," + category + "," + price + "," + isAvailable;
    }
}

class Reservation {
    String customerName;
    int roomNumber;
    String category;
    double amount;

    Reservation(String customerName, int roomNumber, String category, double amount) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amount = amount;
    }

    public String toString() {
        return customerName + "," + roomNumber + "," + category + "," + amount;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<Room>();
    static Scanner sc = new Scanner(System.in);

    static final String ROOM_FILE = "rooms.txt";
    static final String BOOKING_FILE = "bookings.txt";

    public static void main(String[] args) {

        loadRooms();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewAvailableRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelReservation();
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    saveRooms();
                    System.out.println("Thank you for using the system!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Load rooms
    static void loadRooms() {
        File file = new File(ROOM_FILE);

        if (!file.exists()) {
            rooms.add(new Room(101, "Standard", 1500));
            rooms.add(new Room(102, "Standard", 1500));
            rooms.add(new Room(201, "Deluxe", 2500));
            rooms.add(new Room(202, "Deluxe", 2500));
            rooms.add(new Room(301, "Suite", 4000));
            saveRooms();
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Room r = new Room(
                        Integer.parseInt(data[0]),
                        data[1],
                        Double.parseDouble(data[2])
                );
                r.isAvailable = Boolean.parseBoolean(data[3]);
                rooms.add(r);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading rooms.");
        }
    }

    static void saveRooms() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(ROOM_FILE));
            for (Room r : rooms) {
                pw.println(r);
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving rooms.");
        }
    }

    static void viewAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room r : rooms) {
            if (r.isAvailable) {
                System.out.println("Room " + r.roomNumber +
                        " | " + r.category +
                        " | Rs." + r.price);
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        viewAvailableRooms();
        System.out.print("Enter room number to book: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && r.isAvailable) {

                System.out.println("Room Price: Rs." + r.price);
                System.out.print("Confirm payment (yes/no): ");
                String pay = sc.nextLine();

                if (pay.equalsIgnoreCase("yes")) {
                    r.isAvailable = false;
                    saveBooking(new Reservation(name, roomNo, r.category, r.price));
                    saveRooms();
                    System.out.println("Booking successful!");
                    return;
                } else {
                    System.out.println("Payment cancelled.");
                    return;
                }
            }
        }
        System.out.println("Room not available!");
    }

    static void saveBooking(Reservation res) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(BOOKING_FILE, true));
            pw.println(res);
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving booking.");
        }
    }

    static void viewBookings() {
        File file = new File(BOOKING_FILE);

        if (!file.exists()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n--- Booking Details ---");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                System.out.println("Name: " + d[0] +
                        " | Room: " + d[1] +
                        " | Category: " + d[2] +
                        " | Amount: Rs." + d[3]);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading bookings.");
        }
    }

    static void cancelReservation() {
        System.out.print("Enter room number to cancel: ");
        int roomNo = sc.nextInt();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && !r.isAvailable) {
                r.isAvailable = true;
                saveRooms();
                System.out.println("Reservation cancelled successfully.");
                return;
            }
        }
        System.out.println("Reservation not found.");
    }
}
