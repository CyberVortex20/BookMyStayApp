/**
 * UseCase3InventorySetup
 * Demonstrates centralized room inventory management for Book My Stay App
 *
 * @version 3.1
 */
import java.util.*;

class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 10);
        inventory.put("Double", 5);
        inventory.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + change);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms: " + inventory.get(type) + " available");
        }
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nUpdating availability: Booking 1 Single Room...");
        inventory.updateAvailability("Single", -1);

        inventory.displayInventory();
    }
}