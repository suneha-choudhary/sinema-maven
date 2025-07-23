package view.handler;

import model.Booking;
import repository.IBookingRepository;

import java.util.List;

public class ViewBookingsHandler {
    private final IBookingRepository bookingRepo;

    public ViewBookingsHandler(IBookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public void handle() {
        List<Booking> bookings = bookingRepo.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("All Bookings:");
        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.getId()
                    + ", Movie ID: " + booking.getMovieId()
                    + ", Time: " + booking.getTimestamp());
        }
    }
}
