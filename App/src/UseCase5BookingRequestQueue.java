import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {
    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void showAllBookings() {
        for (Reservation r : bookingHistory.getAllReservations()) {
            System.out.println("Reservation ID: " + r.getReservationId() +
                    " | Guest: " + r.getGuestName() +
                    " | Room Type: " + r.getRoomType());
        }
    }

    public void generateSummary() {
        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : bookingHistory.getAllReservations()) {
            countMap.put(r.getRoomType(), countMap.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("Booking Summary:");
        for (String type : countMap.keySet()) {
            System.out.println(type + " Rooms Booked: " + countMap.get(type));
        }
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("R101", "Arun", "Single"));
        history.addReservation(new Reservation("R102", "Bala", "Double"));
        history.addReservation(new Reservation("R103", "Charan", "Suite"));
        history.addReservation(new Reservation("R104", "Divya", "Single"));

        BookingReportService reportService = new BookingReportService(history);

        reportService.showAllBookings();
        System.out.println("--------------------------");
        reportService.generateSummary();
    }
}