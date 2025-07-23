package model;

import java.time.LocalDateTime;

public class Booking {
    private int bookingId;
    private int movieId;
    private LocalDateTime timestamp2;

    public Booking(int movieId, int bookingId, LocalDateTime timestamp) {
        this.movieId = movieId;
        this.bookingId = bookingId;
        this.timestamp2 = timestamp;
    }

    public int getId() { return bookingId; }
    public int getMovieId() { return movieId; }
    public LocalDateTime getTimestamp() { return timestamp2; }
}
