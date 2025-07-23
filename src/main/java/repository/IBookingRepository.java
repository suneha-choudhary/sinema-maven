package repository;

import java.util.List;

import model.Booking;

public interface IBookingRepository {
    void addBooking(int showId, List<Integer> seatIds);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByMovieId(int movieId);
}
