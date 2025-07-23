package service;

import java.util.List;
import model.Seat;

public class BillingService {

    public double calculateTotal(List<Seat> seats) {
        double total = 0;
        for (Seat s : seats) {
            total += s.getPrice();
        }
        return total;
    }
}
