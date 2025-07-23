package repository;

import java.util.List;

import model.Movie;
import model.Show;

public interface IMovieRepository {
    List<Movie> getAllMovies();
    Movie getMovieById(int id);
    void addMovie(Movie movie, String time);
    Boolean removeMovie(int id);
    boolean isMovieExists(String title);
    List<Show> getShowsByMovieId(int movieId);
    boolean deleteShowById(int showId);
}
