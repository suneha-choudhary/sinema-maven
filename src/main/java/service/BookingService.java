package service;

import model.Seat;
import repository.IBookingRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private IBookingRepository bookingRepo;

    public BookingService(IBookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public void bookSeats(int showId, List<Seat> seats) {
        List<Integer> seatIds = seats.stream()
                                    .map(Seat::getSeatId)
                                    .collect(Collectors.toList());

        bookingRepo.addBooking(showId, seatIds);
    }
}
