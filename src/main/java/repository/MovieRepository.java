package repository;

import model.Movie;
import model.Show;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class MovieRepository implements IMovieRepository {

    public MovieRepository() {
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS movies (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(100) NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS shows (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "movie_id INT NOT NULL, " +
                    "show_time TIMESTAMP NOT NULL, " +
                    "FOREIGN KEY (movie_id) REFERENCES movies(id))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovie(Movie movie, String time) {
        try (Connection conn = DBUtil.getConnection()) {
            // Insert movie
            PreparedStatement movieStmt = conn.prepareStatement(
                    "INSERT INTO movies (title) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            movieStmt.setString(1, movie.getTitle());
            movieStmt.executeUpdate();

            ResultSet rs = movieStmt.getGeneratedKeys();
            if (rs.next()) {
                int movieId = rs.getInt(1);

                // Prepare show time
                LocalDate today = LocalDate.now();
                String dateTimeStr = today + " " + time + ":00";
                Timestamp showTime = Timestamp.valueOf(dateTimeStr);

                // Insert show
                PreparedStatement showStmt = conn.prepareStatement(
                        "INSERT INTO shows (movie_id, show_time) VALUES (?, ?)");
                showStmt.setInt(1, movieId);
                showStmt.setTimestamp(2, showTime);
                showStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean removeMovie(int movieId) {
    String deleteShowsSQL = "DELETE FROM shows WHERE movie_id = ?";
    String deleteMovieSQL = "DELETE FROM movies WHERE movie_id = ?";

    try (Connection conn = DBUtil.getConnection()) {
        conn.setAutoCommit(false); // Begin transaction

        try (PreparedStatement deleteShowsStmt = conn.prepareStatement(deleteShowsSQL);
            PreparedStatement deleteMovieStmt = conn.prepareStatement(deleteMovieSQL)) {

            deleteShowsStmt.setInt(1, movieId);
            deleteShowsStmt.executeUpdate();

            deleteMovieStmt.setInt(1, movieId);
            int rowsAffected = deleteMovieStmt.executeUpdate();

            conn.commit(); // Commit transaction
            return rowsAffected > 0;

        } catch (SQLException e) {
            conn.rollback(); // Rollback if anything fails
            e.printStackTrace();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
    }

    @Override
    public List<Movie> getAllMovies() {
        Map<Integer, Movie> movieMap = new LinkedHashMap<>();
        String sql = "SELECT m.movie_id, m.title, s.show_time " +
            "FROM movies m JOIN shows s ON m.movie_id = s.movie_id ORDER BY m.movie_id, s.show_time";

        try (Connection conn = DBUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("movie_id");
                String title = rs.getString("title");
                String time = rs.getString("show_time");

                Movie movie = movieMap.getOrDefault(id, new Movie(id, title));
                movie.addShowTime(time);
                movieMap.put(id, movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(movieMap.values());
    }

    @Override
    public Movie getMovieById(int id) {
        String sql = "SELECT m.id, m.title, s.show_time " +
                     "FROM movies m JOIN shows s ON m.id = s.movie_id WHERE m.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id); // ✅ Add this
            ResultSet rs = pstmt.executeQuery();

            Movie movie = null;
            while (rs.next()) {
                if (movie == null) {
                    String title = rs.getString("title");
                    movie = new Movie(id, title);
                }
                String time = rs.getString("show_time");
                movie.addShowTime(time);
            }

            return movie;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean isMovieExists(String title) {
        String sql = "SELECT COUNT(*) FROM movies WHERE title = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
public List<Show> getShowsByMovieId(int movieId) {
    List<Show> shows = new ArrayList<>();
    String query = "SELECT * FROM shows WHERE movie_id = ?";

    try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, movieId);
        ResultSet rs = ps.executeQuery();

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

@Override
public boolean deleteShowById(int showId) {
    String deleteSeats = "DELETE FROM seats WHERE show_id = ?";
    String deleteShow = "DELETE FROM shows WHERE show_id = ?";

    try (Connection conn = DBUtil.getConnection()) {
        conn.setAutoCommit(false);

        try (PreparedStatement ps1 = conn.prepareStatement(deleteSeats);
             PreparedStatement ps2 = conn.prepareStatement(deleteShow)) {

            ps1.setInt(1, showId);
            ps1.executeUpdate();

            ps2.setInt(1, showId);
            int rows = ps2.executeUpdate();

            conn.commit();
            return rows > 0;

        } catch (SQLException ex) {
            conn.rollback();
            ex.printStackTrace();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

}
