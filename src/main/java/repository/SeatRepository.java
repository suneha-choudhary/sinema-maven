package repository;

import model.Seat;
import model.Seat.SeatType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatRepository implements ISeatRepository {

    @Override
    public List<Seat> getAvailableSeatsByShowId(int showId) {
        List<Seat> availableSeats = new ArrayList<>();

        String query = """
                    SELECT seat_id, seat_number, seat_type, is_booked, price
                    FROM seats
                    WHERE show_id = ? AND is_booked = FALSE
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, showId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("seat_id");
                int number = Integer.parseInt(rs.getString("seat_number")); // assuming seat_number is numeric
                String typeStr = rs.getString("seat_type").toUpperCase();
                SeatType type = SeatType.valueOf(typeStr);
                boolean isBooked = rs.getBoolean("is_booked");
                double price = rs.getDouble("price");

                availableSeats.add(new Seat(id, number, type, isBooked, price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableSeats;
    }

    @Override
    public List<Seat> getAllSeats() {
        List<Seat> allSeats = new ArrayList<>();

        String query = """
                    SELECT seat_id, seat_number, seat_type, is_booked, price
                    FROM seats
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("seat_id");
                int number = Integer.parseInt(rs.getString("seat_number"));
                String typeStr = rs.getString("seat_type").toUpperCase();
                SeatType type = SeatType.valueOf(typeStr);
                boolean isBooked = rs.getBoolean("is_booked");
                double price = rs.getDouble("price");

                allSeats.add(new Seat(id, number, type, isBooked, price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSeats;
    }
}
