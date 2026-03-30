import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }

    public void showServices(String reservationId) {
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());
        System.out.println("Reservation ID: " + reservationId);
        for (Service s : services) {
            System.out.println("Service: " + s.getName() + " | Cost: " + s.getCost());
        }
        System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId1 = "R1001";
        String reservationId2 = "R1002";

        Service wifi = new Service("WiFi", 200);
        Service breakfast = new Service("Breakfast", 300);
        Service spa = new Service("Spa", 1000);

        manager.addService(reservationId1, wifi);
        manager.addService(reservationId1, breakfast);
        manager.addService(reservationId2, spa);

        manager.showServices(reservationId1);
        System.out.println("-----------------------");
        manager.showServices(reservationId2);
    }
}