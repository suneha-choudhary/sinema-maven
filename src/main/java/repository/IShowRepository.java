package repository;
import model.Show;
import java.util.List;

public interface IShowRepository {
    List<Show> getShowsByMovieId(int movieId);
}
