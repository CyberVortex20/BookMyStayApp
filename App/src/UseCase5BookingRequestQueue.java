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

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Suite", 1);
    }

    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);
        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public void showInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

class BookingProcessor implements Runnable {
    private Queue<Reservation> queue;
    private InventoryService inventory;

    public BookingProcessor(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                r = queue.poll();
            }

            if (inventory.allocateRoom(r.getRoomType())) {
                System.out.println(Thread.currentThread().getName() +
                        " Confirmed: " + r.getGuestName() +
                        " | " + r.getRoomType());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " Failed: " + r.getGuestName() +
                        " | " + r.getRoomType());
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Arun", "Single"));
        queue.offer(new Reservation("Bala", "Single"));
        queue.offer(new Reservation("Charan", "Suite"));
        queue.offer(new Reservation("Divya", "Single"));
        queue.offer(new Reservation("Esha", "Suite"));

        InventoryService inventory = new InventoryService();

        Thread t1 = new Thread(new BookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventory), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(queue, inventory), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.showInventory();
    }
}