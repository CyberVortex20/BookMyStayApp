import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int available = getAvailability(type);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }
        inventory.put(type, available - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

class BookingService {
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void processBooking(Reservation reservation) {
        try {
            validate(reservation);
            inventoryService.reduceRoom(reservation.getRoomType());
            System.out.println("Booking Confirmed for " + reservation.getGuestName() +
                    " | Room Type: " + reservation.getRoomType());
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed for " + reservation.getGuestName() +
                    " | Reason: " + e.getMessage());
        }
    }

    private void validate(Reservation reservation) throws InvalidBookingException {
        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (!inventoryService.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + reservation.getRoomType());
        }

        if (inventoryService.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available for type: " + reservation.getRoomType());
        }
    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 0);

        BookingService bookingService = new BookingService(inventory);

        Reservation r1 = new Reservation("Arun", "Single");
        Reservation r2 = new Reservation("Bala", "Double");
        Reservation r3 = new Reservation("", "Single");
        Reservation r4 = new Reservation("Divya", "Suite");

        bookingService.processBooking(r1);
        bookingService.processBooking(r2);
        bookingService.processBooking(r3);
        bookingService.processBooking(r4);
    }
}