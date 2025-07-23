package repository;

import java.util.List;

import model.Seat;

public interface ISeatRepository {
    List<Seat> getAvailableSeatsByShowId(int showId);
    List<Seat> getAllSeats();
}
