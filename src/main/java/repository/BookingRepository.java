package repository;

import model.Booking;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class BookingRepository implements IBookingRepository {
    private final String DB_URL = "jdbc:mysql://localhost:3306/movie_booking_system";
    private final String USER = "root";
    private final String PASS = "9652";

    public BookingRepository() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS bookings (id INTEGER PRIMARY KEY AUTOINCREMENT, movieId INTEGER, timestamp TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public void addBooking(int showId, List<Integer> seatIds) {
    String insertBooking = "INSERT INTO bookings (show_id) VALUES (?)";
    String insertBookingSeats = "INSERT INTO booking_seats (booking_id, seat_id) VALUES (?, ?)";
    String updateSeats = "UPDATE seats SET is_booked = TRUE WHERE seat_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
        conn.setAutoCommit(false);

        try (
            PreparedStatement psBooking = conn.prepareStatement(insertBooking, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psBookingSeat = conn.prepareStatement(insertBookingSeats);
            PreparedStatement psUpdateSeat = conn.prepareStatement(updateSeats)
        ) {
            // Insert booking
            psBooking.setInt(1, showId);
            psBooking.executeUpdate();

            ResultSet rs = psBooking.getGeneratedKeys();
            if (rs.next()) {
                int bookingId = rs.getInt(1);

                for (int seatId : seatIds) {
                    // Insert into booking_seats
                    psBookingSeat.setInt(1, bookingId);
                    psBookingSeat.setInt(2, seatId);
                    psBookingSeat.addBatch();

                    // Update seat as booked
                    psUpdateSeat.setInt(1, seatId);
                    psUpdateSeat.addBatch();
                }

                psBookingSeat.executeBatch();
                psUpdateSeat.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM bookings")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int movieId = rs.getInt("movieId");
                LocalDateTime timestamp = LocalDateTime.parse(rs.getString("timestamp"));
                bookings.add(new Booking(id, movieId, timestamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByMovieId(int movieId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = """
                    SELECT b.booking_id, s.movie_id, b.booking_time
                    FROM bookings b
                    JOIN shows s ON b.show_id = s.show_id
                    WHERE s.movie_id = ?
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int fetchedMovieId = rs.getInt("movie_id");
                LocalDateTime bookingTime = rs.getTimestamp("booking_time").toLocalDateTime();

                bookings.add(new Booking(fetchedMovieId, bookingId, bookingTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

}
