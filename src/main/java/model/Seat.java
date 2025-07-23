package model;

public class Seat {

    public enum SeatType {
        SILVER, GOLD, PLATINUM
    }

    private int seatId;
    private int seatNumber;
    private SeatType seatType;
    private boolean isBooked;
    private double price;

    public Seat(int seatId, int seatNumber, SeatType seatType, boolean isBooked, double price) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.isBooked = isBooked;
        this.price = price;
    }

    // Getters
    public int getSeatId() {
        return seatId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public double getPrice() {
        return price;
    }

    // Optional: Setters if you plan to modify seat data
    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
