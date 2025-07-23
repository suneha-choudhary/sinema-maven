package view.handler;

import model.Movie;
import model.Show;
import repository.IMovieRepository;

import java.util.List;
import java.util.Scanner;

public class DeleteShowHandler {
    private final IMovieRepository movieRepo;
    private final Scanner sc;

    public DeleteShowHandler(IMovieRepository movieRepo, Scanner sc) {
        this.movieRepo = movieRepo;
        this.sc = sc;
    }

    public void handle() {
        List<Movie> movies = movieRepo.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        System.out.println("\nAvailable Movies:");
        for (Movie movie : movies) {
            System.out.printf("Movie ID: %d, Name: %s\n", movie.getId(), movie.getTitle());
        }

        System.out.print("\nEnter Movie ID to view its shows: ");
        int movieId;
        try {
            movieId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid movie ID.");
            return;
        }

        List<Show> shows = movieRepo.getShowsByMovieId(movieId);
        if (shows.isEmpty()) {
            System.out.println("No shows found for this movie.");
            return;
        }

        System.out.println("\nShows for selected movie:");
        for (Show show : shows) {
            System.out.printf("Show ID: %d, Time: %s\n", show.getId(), show.getTime());
        }

        System.out.print("\nEnter Show ID to delete: ");
        int showId;
        try {
            showId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid show ID.");
            return;
        }

        boolean success = movieRepo.deleteShowById(showId);
        if (success) {
            System.out.println("Show deleted successfully.");
        } else {
            System.out.println("Failed to delete show. Check if it exists or has bookings.");
        }
    }
}
