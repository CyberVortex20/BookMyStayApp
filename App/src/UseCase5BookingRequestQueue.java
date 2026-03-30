import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void increaseRoom(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class CancellationService {
    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    public void addBooking(Reservation reservation) {
        confirmedBookings.put(reservation.getReservationId(), reservation);
    }

    public void cancelBooking(String reservationId, InventoryService inventoryService) {
        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid Reservation ID");
            return;
        }

        Reservation r = confirmedBookings.get(reservationId);

        rollbackStack.push(r.getRoomId());
        inventoryService.increaseRoom(r.getRoomType());

        confirmedBookings.remove(reservationId);

        System.out.println("Cancellation Successful for " + r.getGuestName() +
                " | Room ID: " + r.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Suite", 0);

        CancellationService cancellationService = new CancellationService();

        Reservation r1 = new Reservation("R201", "Arun", "Single", "S12345");
        Reservation r2 = new Reservation("R202", "Bala", "Suite", "SU54321");

        cancellationService.addBooking(r1);
        cancellationService.addBooking(r2);

        cancellationService.cancelBooking("R201", inventory);
        cancellationService.cancelBooking("R999", inventory);

        cancellationService.showRollbackStack();

        System.out.println("Updated Inventory:");
        System.out.println("Single: " + inventory.getAvailability("Single"));
        System.out.println("Suite: " + inventory.getAvailability("Suite"));
    }
}