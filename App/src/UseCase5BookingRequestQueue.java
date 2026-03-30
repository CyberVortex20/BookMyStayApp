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

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public void showQueue() {
        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName() + " | Room Type: " + r.getRoomType());
        }
    }
}


public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Arun", "Single");
        Reservation r2 = new Reservation("Bala", "Double");
        Reservation r3 = new Reservation("Charan", "Suite");
        Reservation r4 = new Reservation("Divya", "Single");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        bookingQueue.addRequest(r4);

        bookingQueue.showQueue();
    }
}