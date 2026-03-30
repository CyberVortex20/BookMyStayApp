import java.util.*;

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
        return inventory.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

class BookingService {
    private Queue<Reservation> queue;
    private InventoryService inventoryService;
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();
    private Set<String> allRoomIds = new HashSet<>();

    public BookingService(Queue<Reservation> queue, InventoryService inventoryService) {
        this.queue = queue;
        this.inventoryService = inventoryService;
    }

    public void processBookings() {
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            String type = r.getRoomType();

            if (inventoryService.getAvailability(type) > 0) {
                String roomId = generateRoomId(type);

                if (!allRoomIds.contains(roomId)) {
                    allRoomIds.add(roomId);
                    allocatedRooms.putIfAbsent(type, new HashSet<>());
                    allocatedRooms.get(type).add(roomId);

                    inventoryService.reduceRoom(type);

                    System.out.println("Booking Confirmed for " + r.getGuestName() +
                            " | Room Type: " + type +
                            " | Room ID: " + roomId);
                }
            } else {
                System.out.println("Booking Failed for " + r.getGuestName() +
                        " | Room Type: " + type + " not available");
            }
        }
    }

    private String generateRoomId(String type) {
        return type.substring(0, 1).toUpperCase() + UUID.randomUUID().toString().substring(0, 5);
    }
}

public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Arun", "Single"));
        queue.offer(new Reservation("Bala", "Single"));
        queue.offer(new Reservation("Charan", "Suite"));
        queue.offer(new Reservation("Divya", "Single"));

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Suite", 1);

        BookingService bookingService = new BookingService(queue, inventory);
        bookingService.processBookings();
    }
}