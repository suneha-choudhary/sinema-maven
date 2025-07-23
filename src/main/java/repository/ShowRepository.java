package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Show;

public class ShowRepository implements IShowRepository {

    @Override
    public List<Show> getShowsByMovieId(int movieId) {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT show_id, movie_id, show_time FROM shows WHERE movie_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Show show = new Show();
                show.setId(rs.getInt("show_id"));
                show.setMovieId(rs.getInt("movie_id"));
                show.setTime(rs.getString("show_time"));
                shows.add(show);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shows;
    }
    
}
