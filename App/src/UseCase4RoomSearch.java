import java.util.*;

class Room {
    private String type;
    private double price;
    private List<String> amenities;

    public Room(String type, double price, List<String> amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getAmenities() {
        return amenities;
    }
}

class Inventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();

    public void addRoom(String type, int count) {
        roomAvailability.put(type, count);
    }

    public int getAvailability(String type) {
        return roomAvailability.getOrDefault(type, 0);
    }

    public Set<String> getRoomTypes() {
        return roomAvailability.keySet();
    }
}

class SearchService {
    private Inventory inventory;
    private Map<String, Room> roomMap;

    public SearchService(Inventory inventory, Map<String, Room> roomMap) {
        this.inventory = inventory;
        this.roomMap = roomMap;
    }

    public void searchAvailableRooms() {
        for (String type : inventory.getRoomTypes()) {
            int available = inventory.getAvailability(type);
            if (available > 0 && roomMap.containsKey(type)) {
                Room room = roomMap.get(type);
                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available: " + available);
                System.out.println("---------------------------");
            }
        }
    }
}

public class UseCase4RoomSearch {
    public static void main(String[] args) {
        Room single = new Room("Single", 2000, Arrays.asList("WiFi", "AC"));
        Room doubleRoom = new Room("Double", 3500, Arrays.asList("WiFi", "AC", "TV"));
        Room suite = new Room("Suite", 5000, Arrays.asList("WiFi", "AC", "TV", "Mini Bar"));

        Map<String, Room> roomMap = new HashMap<>();
        roomMap.put(single.getType(), single);
        roomMap.put(doubleRoom.getType(), doubleRoom);
        roomMap.put(suite.getType(), suite);

        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 3);
        inventory.addRoom("Double", 0);
        inventory.addRoom("Suite", 2);

        SearchService searchService = new SearchService(inventory, roomMap);
        searchService.searchAvailableRooms();
    }
}