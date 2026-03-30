import java.io.*;
import java.util.*;

class Reservation implements Serializable {
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

class InventoryService implements Serializable {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }
}

class BookingHistory implements Serializable {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getHistory() {
        return history;
    }

    public void setHistory(List<Reservation> history) {
        this.history = history;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_data.ser";

    public void save(InventoryService inventory, BookingHistory history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("Data saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public Object[] load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            InventoryService inventory = (InventoryService) ois.readObject();
            BookingHistory history = (BookingHistory) ois.readObject();
            System.out.println("Data loaded successfully");
            return new Object[]{inventory, history};
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        PersistenceService persistenceService = new PersistenceService();

        Object[] data = persistenceService.load();

        InventoryService inventory;
        BookingHistory history;

        if (data != null) {
            inventory = (InventoryService) data[0];
            history = (BookingHistory) data[1];
        } else {
            inventory = new InventoryService();
            inventory.addRoom("Single", 2);
            inventory.addRoom("Suite", 1);

            history = new BookingHistory();
        }


        history.addReservation(new Reservation("R301", "Arun", "Single"));
        history.addReservation(new Reservation("R302", "Bala", "Suite"));

        persistenceService.save(inventory, history);

        System.out.println("Current Inventory: " + inventory.getInventory());

        for (Reservation r : history.getHistory()) {
            System.out.println("Reservation: " + r.getReservationId() +
                    " | " + r.getGuestName() +
                    " | " + r.getRoomType());
        }
    }
}