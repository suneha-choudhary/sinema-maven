package view.handler;

import java.util.List;

import model.Movie;
import repository.IMovieRepository;

public class ViewMoviesHandler {
    private final IMovieRepository MovieRepository;

    public ViewMoviesHandler(IMovieRepository movieRepository) {
        this.MovieRepository = movieRepository;
    }

    public void handle() {
        List<Movie> movies = MovieRepository.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
        } else {
            System.out.println("Movies:");
            for (Movie movie : movies) {
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Show Times:");
                for (String time : movie.getShowTimes()) {
                    System.out.println("  - " + time);
                }
                System.out.println();
            }
        }

    }
}
